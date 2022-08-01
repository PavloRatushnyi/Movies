package com.pavelratushnyi.movies.data.movies

import app.cash.turbine.test
import com.pavelratushnyi.movies.data.movies.local.FakeLocalMoviesDataSource
import com.pavelratushnyi.movies.data.movies.remote.RemoteMoviesDataSource
import com.pavelratushnyi.movies.domain.Resource
import com.pavelratushnyi.movies.domain.vo.Movie
import com.pavelratushnyi.movies.domain.vo.MovieDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

@ExperimentalCoroutinesApi
internal class MoviesRepositoryImplTest {

    private val localMoviesDataSource = spy(FakeLocalMoviesDataSource())
    private val remoteDataSource: RemoteMoviesDataSource = mock()

    private val repository = MoviesRepositoryImpl(localMoviesDataSource, remoteDataSource)

    @AfterEach
    fun reset() {
        localMoviesDataSource.reset()
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
            localMoviesDataSource.insertPopularMovies(moviesLocal)
            whenever(remoteDataSource.getPopularMovies()).thenReturn(moviesRemote)

            repository.getPopularMovies().test {
                assertEquals(Resource.Loading<List<Movie>>(), awaitItem())
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
            localMoviesDataSource.insertPopularMovies(moviesLocal)
            val moviesRemoteError = IllegalStateException("no internet")
            whenever(remoteDataSource.getPopularMovies()).thenThrow(moviesRemoteError)

            repository.getPopularMovies().test {
                assertEquals(Resource.Loading<List<Movie>>(), awaitItem())
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
                assertEquals(Resource.Loading<List<Movie>>(), awaitItem())
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
                assertEquals(Resource.Loading<List<Movie>>(), awaitItem())
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

            verifyNoInteractions(localMoviesDataSource)
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

            verify(localMoviesDataSource).insertPopularMovies(moviesRemote)
        }

    @Test
    fun `GIVEN favourite movies in local cache WHEN requesting favourite movies THEN movies from local data source returned`() =
        runTest {
            val firstMovie = Movie(
                id = 1,
                title = "movie title 1",
                overview = "movie overview 1",
                posterPath = "movie poster path 1"
            )
            val secondMovie = Movie(
                id = 2,
                title = "movie title 2",
                overview = "movie overview 2",
                posterPath = "movie poster path 2"
            )
            val moviesLocal = listOf(firstMovie, secondMovie)
            localMoviesDataSource.insertPopularMovies(moviesLocal)
            localMoviesDataSource.addToFavourites(2)

            repository.getFavouriteMovies().test {
                assertEquals(Resource.Loading<List<Movie>>(), awaitItem())
                assertEquals(Resource.Success(listOf(secondMovie)), awaitItem())
                expectNoEvents()
            }
        }

    @Test
    fun `GIVEN favourite movies in local cache WHEN requesting favourite movies ids THEN movies ids from local data source returned`() =
        runTest {
            localMoviesDataSource.addToFavourites(2)

            repository.getFavouriteMoviesIds().test {
                assertEquals(Resource.Loading<List<Long>>(), awaitItem())
                assertEquals(Resource.Success(listOf(2L)), awaitItem())
                expectNoEvents()
            }
        }

    @Test
    fun `WHEN adding movie to favourites THEN movie id added to favourites`() =
        runTest {
            repository.addToFavourites(2)

            localMoviesDataSource.getFavouriteMoviesIds().test {
                assertEquals(listOf(2L), expectMostRecentItem())
                expectNoEvents()
            }
        }

    @Test
    fun `WHEN removing movie from favourites THEN movie id removed from favourites`() =
        runTest {
            localMoviesDataSource.addToFavourites(2)

            repository.removeFromFavourites(2)

            localMoviesDataSource.getFavouriteMoviesIds().test {
                assertEquals(emptyList<Long>(), expectMostRecentItem())
                expectNoEvents()
            }
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
                assertEquals(Resource.Loading<MovieDetails>(), awaitItem())
                assertEquals(Resource.Success(movieDetails), awaitItem())
                expectNoEvents()
            }

            assertEquals(movieDetails, localMoviesDataSource.getMovieDetails(1).first())
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
            localMoviesDataSource.insertMovieDetails(movieDetailsLocal)
            whenever(remoteDataSource.getMovieDetails(1)).thenReturn(movieDetailsRemote)

            repository.getMovieDetails(1).test {
                assertEquals(Resource.Loading<MovieDetails>(), awaitItem())
                assertEquals(Resource.Loading(movieDetailsLocal), awaitItem())
                assertEquals(Resource.Success(movieDetailsRemote), awaitItem())
                expectNoEvents()
            }

            assertEquals(movieDetailsRemote, localMoviesDataSource.getMovieDetails(1).first())
        }

    @Test
    fun `WHEN refreshing movie details AND error thrown THEN failure returned AND local storage not updated`() =
        runTest {
            val movieDetailsRemoteError = IllegalStateException("no internet")
            whenever(remoteDataSource.getMovieDetails(1)).thenThrow(movieDetailsRemoteError)

            assertEquals(Result.failure<Unit>(movieDetailsRemoteError), repository.refreshMovieDetails(1))

            verifyNoInteractions(localMoviesDataSource)
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

            verify(localMoviesDataSource).insertMovieDetails(movieDetailsRemote)
        }
}