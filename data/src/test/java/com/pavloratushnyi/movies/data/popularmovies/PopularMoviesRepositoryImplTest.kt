package com.pavloratushnyi.movies.data.popularmovies

import app.cash.turbine.test
import com.pavloratushnyi.movies.data.popularmovies.local.FakeLocalPopularMoviesDataSource
import com.pavloratushnyi.movies.data.popularmovies.remote.RemotePopularMoviesDataSource
import com.pavloratushnyi.movies.domain.Resource
import com.pavloratushnyi.movies.domain.vo.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
internal class PopularMoviesRepositoryImplTest {

    private val localDataSource = spy(FakeLocalPopularMoviesDataSource())
    private val remoteDataSource: RemotePopularMoviesDataSource = mock()

    private val repository = PopularMoviesRepositoryImpl(localDataSource, remoteDataSource)

    @AfterEach
    fun reset() {
        localDataSource.reset()
    }

    @Test
    fun `GIVEN popular movies in local cache WHEN requesting popular movies THEN movies from local and then from remote data source returned`() =
        runTest {
            val moviesLocal = listOf(
                Movie(
                    id = 1,
                    title = "movie title 1",
                    overview = "movie overview 1",
                    posterPath = "movie poster path 1"
                )
            )
            val moviesRemote = listOf(
                Movie(
                    id = 2,
                    title = "movie title 2",
                    overview = "movie overview 2",
                    posterPath = "movie poster path 2"
                )
            )
            localDataSource.insertPopularMovies(moviesLocal)
            whenever(remoteDataSource.getPopularMovies()).thenReturn(moviesRemote)

            repository.getPopularMovies().test {
                assertEquals(Resource.Loading(moviesLocal), awaitItem())
                assertEquals(Resource.Success(moviesRemote), awaitItem())
                expectNoEvents()
            }
        }

    @Test
    fun `GIVEN popular movies in local cache WHEN requesting popular movies AND remote fetching failed THEN movies from local data source and then resource error with local movies returned`() =
        runTest {
            val moviesLocal = listOf(
                Movie(
                    id = 1,
                    title = "movie title 1",
                    overview = "movie overview 1",
                    posterPath = "movie poster path 1"
                )
            )
            localDataSource.insertPopularMovies(moviesLocal)
            val moviesRemoteError = IllegalStateException("no internet")
            whenever(remoteDataSource.getPopularMovies()).thenThrow(moviesRemoteError)

            repository.getPopularMovies().test {
                assertEquals(Resource.Loading(moviesLocal), awaitItem())
                assertEquals(Resource.Error(moviesRemoteError, moviesLocal), awaitItem())
                assertEquals(Resource.Success(moviesLocal), awaitItem())
                expectNoEvents()
            }
        }

    @Test
    fun `GIVEN no popular movies in local cache WHEN requesting popular movies THEN movies from remote data source returned`() =
        runTest {
            val moviesRemote = listOf(
                Movie(
                    id = 2,
                    title = "movie title 2",
                    overview = "movie overview 2",
                    posterPath = "movie poster path 2"
                )
            )
            whenever(remoteDataSource.getPopularMovies()).thenReturn(moviesRemote)

            repository.getPopularMovies().test {
                assertEquals(Resource.Success(moviesRemote), awaitItem())
                expectNoEvents()
            }
        }

    @Test
    fun `GIVEN no popular movies in local cache WHEN requesting popular movies AND remote fetching failed THEN resource error returned`() =
        runTest {
            val moviesRemoteError = IllegalStateException("no internet")
            whenever(remoteDataSource.getPopularMovies()).thenThrow(moviesRemoteError)

            repository.getPopularMovies().test {
                assertEquals(Resource.Error<List<Movie>>(moviesRemoteError), awaitItem())
                expectNoEvents()
            }
        }

    @Test
    fun `WHEN refreshing popular movies AND error thrown THEN failure returned AND local storage not updated`() =
        runTest {
            val moviesRemoteError = IllegalStateException("no internet")
            whenever(remoteDataSource.getPopularMovies()).thenThrow(moviesRemoteError)

            assertEquals(Result.failure<Unit>(moviesRemoteError), repository.refreshPopularMovies())

            verifyNoInteractions(localDataSource)
        }

    @Test
    fun `WHEN refreshing popular movies AND movies refreshed THEN local storage updated AND success returned`() =
        runTest {
            val moviesRemote = listOf(
                Movie(
                    id = 1,
                    title = "movie title 1",
                    overview = "movie overview 1",
                    posterPath = "movie poster path 1"
                )
            )
            whenever(remoteDataSource.getPopularMovies()).thenReturn(moviesRemote)

            assertEquals(Result.success(Unit), repository.refreshPopularMovies())

            verify(localDataSource).insertPopularMovies(moviesRemote)
        }
}