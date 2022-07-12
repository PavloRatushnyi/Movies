package com.pavelratushnyi.movies.data.movies

import app.cash.turbine.test
import com.pavelratushnyi.movies.data.Resource
import com.pavelratushnyi.movies.data.movies.local.LocalMoviesDataSource
import com.pavelratushnyi.movies.data.movies.remote.RemoteMoviesDataSource
import com.pavelratushnyi.movies.domain.vo.Movie
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub

internal class MoviesRepositoryImplTest {

    private val localMoviesDataSource: LocalMoviesDataSource = mock()
    private val remoteDataSource: RemoteMoviesDataSource = mock()

    private val repository = MoviesRepositoryImpl(localMoviesDataSource, remoteDataSource)

    @Test
    fun `GIVEN popular movies in local cache WHEN requesting popular movies THEN movies from local and then from remote data source returned`() {
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
        localMoviesDataSource.stub {
            onBlocking { getPopularMovies() } doReturn moviesLocal
        }
        remoteDataSource.stub {
            onBlocking { getPopularMovies() } doReturn moviesRemote
        }

        runBlocking {
            repository.getPopularMovies().test {
                assertEquals(Resource.Loading<List<Movie>>(), awaitItem())
                assertEquals(Resource.Loading(moviesLocal), awaitItem())
                assertEquals(Resource.Success(moviesRemote), awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `GIVEN popular movies in local cache WHEN requesting popular movies AND remote fetching failed THEN movies from local data source and then resource error with local movies returned`() {
        val moviesLocal = listOf(
            Movie(
                id = 1,
                title = "movie title 1",
                overview = "movie overview 1",
                posterPath = "movie poster path 1"
            )
        )
        val moviesRemoteError = IllegalStateException("no internet")
        localMoviesDataSource.stub {
            onBlocking { getPopularMovies() } doReturn moviesLocal
        }
        remoteDataSource.stub {
            onBlocking { getPopularMovies() } doThrow moviesRemoteError
        }

        runBlocking {
            repository.getPopularMovies().test {
                assertEquals(Resource.Loading<List<Movie>>(), awaitItem())
                assertEquals(Resource.Loading(moviesLocal), awaitItem())
                assertEquals(Resource.Error(moviesRemoteError, moviesLocal), awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `GIVEN no popular movies in local cache WHEN requesting popular movies THEN movies from remote data source returned`() {
        val moviesLocal = null
        val moviesRemote = listOf(
            Movie(
                id = 2,
                title = "movie title 2",
                overview = "movie overview 2",
                posterPath = "movie poster path 2"
            )
        )
        localMoviesDataSource.stub {
            onBlocking { getPopularMovies() } doReturn moviesLocal
        }
        remoteDataSource.stub {
            onBlocking { getPopularMovies() } doReturn moviesRemote
        }

        runBlocking {
            repository.getPopularMovies().test {
                assertEquals(Resource.Loading<List<Movie>>(), awaitItem())
                assertEquals(Resource.Success(moviesRemote), awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `GIVEN no popular movies in local cache WHEN requesting popular movies AND remote fetching failed THEN resource error returned`() {
        val moviesLocal = null
        val moviesRemoteError = IllegalStateException("no internet")
        localMoviesDataSource.stub {
            onBlocking { getPopularMovies() } doReturn moviesLocal
        }
        remoteDataSource.stub {
            onBlocking { getPopularMovies() } doThrow moviesRemoteError
        }

        runBlocking {
            repository.getPopularMovies().test {
                assertEquals(Resource.Loading<List<Movie>>(), awaitItem())
                assertEquals(Resource.Error<List<Movie>>(moviesRemoteError), awaitItem())
                awaitComplete()
            }
        }
    }
}