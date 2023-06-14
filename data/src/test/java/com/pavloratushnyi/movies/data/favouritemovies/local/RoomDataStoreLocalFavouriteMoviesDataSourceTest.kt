package com.pavloratushnyi.movies.data.favouritemovies.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import app.cash.turbine.test
import com.pavloratushnyi.model.Movie
import com.pavloratushnyi.movies.data.FakeDataStore
import com.pavloratushnyi.movies.data.favouritemovies.local.RoomDataStoreLocalFavouriteMoviesDataSource.Companion.FAVOURITE_MOVIES_IDS_KEY
import com.pavloratushnyi.movies.data.movies.local.MoviesDao
import com.pavloratushnyi.movies.data.popularmovies.toEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
internal class RoomDataStoreLocalFavouriteMoviesDataSourceTest {

    private val moviesDao: MoviesDao = mock()
    private val dataStore: DataStore<Preferences> = FakeDataStore()

    private val dataSource = RoomDataStoreLocalFavouriteMoviesDataSource(moviesDao, dataStore)

    @Test
    fun `GIVEN favourite movies ids WHEN getting favourite movies THEN movies from dao returned and sorted according to id position`() =
        runTest {
            dataStore.edit { preferences ->
                preferences[
                        stringSetPreferencesKey(FAVOURITE_MOVIES_IDS_KEY)
                ] = linkedSetOf("1", "2", "3")
            }
            val movies = listOf(
                Movie(
                    id = 1,
                    title = "movie title 1",
                    overview = "movie overview 1",
                    posterPath = "movie poster path 1"
                ),
                Movie(
                    id = 2,
                    title = "movie title 2",
                    overview = "movie overview 2",
                    posterPath = "movie poster path 2"
                ),
                Movie(
                    id = 3,
                    title = "movie title 3",
                    overview = "movie overview 3",
                    posterPath = "movie poster path 3"
                ),
            )
            whenever(moviesDao.getAndSortByIds(listOf(1L, 2L, 3L))).thenReturn(flowOf(movies.map {
                it.toEntity()
            }))

            dataSource.getFavouriteMovies().test {
                assertEquals(movies, awaitItem())
                expectNoEvents()
            }
        }

    @Test
    fun `GIVEN favourite movies ids WHEN getting favourite movies ids THEN movies ids returned`() =
        runTest {
            dataStore.edit { preferences ->
                preferences[
                        stringSetPreferencesKey(FAVOURITE_MOVIES_IDS_KEY)
                ] = linkedSetOf("1", "2", "3")
            }

            dataSource.getFavouriteMoviesIds().test {
                assertEquals(listOf(1L, 2L, 3L), awaitItem())
                expectNoEvents()
            }
        }

    @Test
    fun `WHEN adding to favourites THEN movie id added to favourites`() =
        runTest {
            dataStore.edit { preferences ->
                preferences[
                        stringSetPreferencesKey(FAVOURITE_MOVIES_IDS_KEY)
                ] = linkedSetOf("1", "2", "3")
            }

            dataSource.addToFavourites(4)

            dataStore.data.test {
                assertEquals(
                    linkedSetOf("4", "1", "2", "3"),
                    awaitItem()[stringSetPreferencesKey(FAVOURITE_MOVIES_IDS_KEY)]
                )
                expectNoEvents()
            }
        }

    @Test
    fun `WHEN removing from favourites THEN movie id removed from favourites`() =
        runTest {
            dataStore.edit { preferences ->
                preferences[
                        stringSetPreferencesKey(FAVOURITE_MOVIES_IDS_KEY)
                ] = linkedSetOf("1", "2", "3")
            }

            dataSource.removeFromFavourites(2)

            dataStore.data.test {
                assertEquals(
                    linkedSetOf("1", "3"),
                    awaitItem()[stringSetPreferencesKey(FAVOURITE_MOVIES_IDS_KEY)]
                )
                expectNoEvents()
            }
        }
}