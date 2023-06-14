package com.pavloratushnyi.movies.data.popularmovies.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import app.cash.turbine.test
import com.pavloratushnyi.model.Movie
import com.pavloratushnyi.movies.data.FakeDataStore
import com.pavloratushnyi.movies.data.movies.local.MoviesDao
import com.pavloratushnyi.movies.data.popularmovies.local.RoomDataStoreLocalPopularMoviesDataSource.Companion.POPULAR_MOVIES_IDS_KEY
import com.pavloratushnyi.movies.data.popularmovies.toEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
internal class RoomDataStoreLocalPopularMoviesDataSourceTest {

    private val moviesDao: MoviesDao = mock()
    private val dataStore: DataStore<Preferences> = FakeDataStore()

    private val dataSource = RoomDataStoreLocalPopularMoviesDataSource(moviesDao, dataStore)

    @Test
    fun `GIVEN no popular movies ids WHEN getting popular movies THEN no movies returned`() =
        runTest {
            dataStore.edit { preferences ->
                preferences.remove(stringSetPreferencesKey(POPULAR_MOVIES_IDS_KEY))
            }

            dataSource.getPopularMovies().test {
                assertEquals(null, awaitItem())
                expectNoEvents()
            }
        }

    @Test
    fun `GIVEN popular movies ids WHEN getting popular movies THEN movies from dao returned and sorted according to id position`() =
        runTest {
            dataStore.edit { preferences ->
                preferences[
                        stringSetPreferencesKey(POPULAR_MOVIES_IDS_KEY)
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

            dataSource.getPopularMovies().test {
                assertEquals(movies, awaitItem())
                expectNoEvents()
            }
        }

    @Test
    fun `WHEN inserting popular movies THEN movies ids saved to data store AND movies stored to room`() =
        runTest {
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
                )
            )
            dataSource.insertPopularMovies(movies)

            dataStore.data.test {
                assertEquals(
                    movies.map { it.id.toString() }.toSet(),
                    awaitItem()[stringSetPreferencesKey(POPULAR_MOVIES_IDS_KEY)]
                )
                expectNoEvents()
            }
            verify(moviesDao).insert(movies.map { it.toEntity() })
        }
}

