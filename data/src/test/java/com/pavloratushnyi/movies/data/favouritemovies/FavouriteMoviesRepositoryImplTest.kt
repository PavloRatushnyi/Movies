package com.pavloratushnyi.movies.data.favouritemovies

import app.cash.turbine.test
import com.pavloratushnyi.movies.data.favouritemovies.local.FakeLocalFavouriteMoviesDataSource
import com.pavloratushnyi.movies.model.Movie
import com.pavloratushnyi.resource.Resource
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.spy

internal class FavouriteMoviesRepositoryImplTest {

    private val localDataSource = spy(FakeLocalFavouriteMoviesDataSource())

    private val repository = FavouriteMoviesRepositoryImpl(localDataSource)

    @AfterEach
    fun reset() {
        localDataSource.reset()
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
            localDataSource.insertMovies(moviesLocal)
            localDataSource.addToFavourites(2)

            repository.getFavouriteMovies().test {
                assertEquals(Resource.Success(listOf(secondMovie)), awaitItem())
                expectNoEvents()
            }
        }

    @Test
    fun `GIVEN favourite movies in local cache WHEN requesting favourite movies ids THEN movies ids from local data source returned`() =
        runTest {
            localDataSource.addToFavourites(2)

            repository.getFavouriteMoviesIds().test {
                assertEquals(Resource.Success(listOf(2L)), awaitItem())
                expectNoEvents()
            }
        }

    @Test
    fun `WHEN adding movie to favourites THEN movie id added to favourites`() =
        runTest {
            repository.addToFavourites(2)

            localDataSource.getFavouriteMoviesIds().test {
                assertEquals(listOf(2L), expectMostRecentItem())
                expectNoEvents()
            }
        }

    @Test
    fun `WHEN removing movie from favourites THEN movie id removed from favourites`() =
        runTest {
            localDataSource.addToFavourites(2)

            repository.removeFromFavourites(2)

            localDataSource.getFavouriteMoviesIds().test {
                assertEquals(emptyList<Long>(), expectMostRecentItem())
                expectNoEvents()
            }
        }
}