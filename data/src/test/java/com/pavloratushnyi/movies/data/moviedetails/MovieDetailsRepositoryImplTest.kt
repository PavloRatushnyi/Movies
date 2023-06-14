package com.pavloratushnyi.movies.data.moviedetails

import app.cash.turbine.test
import com.pavloratushnyi.model.MovieDetails
import com.pavloratushnyi.movies.data.moviedetails.local.FakeLocalMovieDetailsDataSource
import com.pavloratushnyi.movies.data.moviedetails.remote.RemoteMovieDetailsDataSource
import com.pavloratushnyi.movies.domain.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
internal class MovieDetailsRepositoryImplTest {

    private val localDataSource = spy(FakeLocalMovieDetailsDataSource())
    private val remoteDataSource: RemoteMovieDetailsDataSource = mock()

    private val repository = MovieDetailsRepositoryImpl(localDataSource, remoteDataSource)

    @AfterEach
    fun reset() {
        localDataSource.reset()
    }

    @Test
    fun `GIVEN no movie details in cache WHEN requesting movie details THEN movies from remote data source returned AND saved to cache`() =
        runTest {
            val movieDetails = MovieDetails(
                id = 1,
                title = "movie title",
                overview = "movie overview",
                posterPath = "movie poster path",
                genres = emptyList(),
                productionCompanies = emptyList(),
                productionCountries = emptyList()
            )
            whenever(remoteDataSource.getMovieDetails(1)).thenReturn(movieDetails)

            repository.getMovieDetails(1).test {
                assertEquals(Resource.Success(movieDetails), awaitItem())
                expectNoEvents()
            }

            assertEquals(movieDetails, localDataSource.getMovieDetails(1).first())
        }

    @Test
    fun `GIVEN movie details in cache WHEN requesting movie details THEN movies from cache and then from remote data source returned AND saved to cache`() =
        runTest {
            val movieDetailsLocal = MovieDetails(
                id = 1,
                title = "movie title",
                overview = "movie overview old",
                posterPath = "movie poster path",
                genres = emptyList(),
                productionCompanies = emptyList(),
                productionCountries = emptyList()
            )
            val movieDetailsRemote = MovieDetails(
                id = 1,
                title = "movie title",
                overview = "movie overview",
                posterPath = "movie poster path",
                genres = emptyList(),
                productionCompanies = emptyList(),
                productionCountries = emptyList()
            )
            localDataSource.insertMovieDetails(movieDetailsLocal)
            whenever(remoteDataSource.getMovieDetails(1)).thenReturn(movieDetailsRemote)

            repository.getMovieDetails(1).test {
                assertEquals(Resource.Loading(movieDetailsLocal), awaitItem())
                assertEquals(Resource.Success(movieDetailsRemote), awaitItem())
                expectNoEvents()
            }

            assertEquals(movieDetailsRemote, localDataSource.getMovieDetails(1).first())
        }

    @Test
    fun `WHEN refreshing movie details AND error thrown THEN failure returned AND local storage not updated`() =
        runTest {
            val movieDetailsRemoteError = IllegalStateException("no internet")
            whenever(remoteDataSource.getMovieDetails(1)).thenThrow(movieDetailsRemoteError)

            assertEquals(
                Result.failure<Unit>(movieDetailsRemoteError),
                repository.refreshMovieDetails(1)
            )

            verifyNoInteractions(localDataSource)
        }

    @Test
    fun `WHEN refreshing popular movie details AND movie details refreshed THEN local storage updated AND success returned`() =
        runTest {
            val movieDetailsRemote = MovieDetails(
                id = 1,
                title = "title",
                overview = "overview",
                posterPath = "posterPath",
                genres = emptyList(),
                productionCountries = emptyList(),
                productionCompanies = emptyList()
            )
            whenever(remoteDataSource.getMovieDetails(1)).thenReturn(movieDetailsRemote)

            assertEquals(Result.success(Unit), repository.refreshMovieDetails(1))

            verify(localDataSource).insertMovieDetails(movieDetailsRemote)
        }
}