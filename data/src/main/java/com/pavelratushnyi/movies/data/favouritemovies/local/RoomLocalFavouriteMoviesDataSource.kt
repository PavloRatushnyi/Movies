package com.pavelratushnyi.movies.data.favouritemovies.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.pavelratushnyi.movies.data.popularmovies.local.MoviesDao
import com.pavelratushnyi.movies.data.popularmovies.toDomain
import com.pavelratushnyi.movies.domain.vo.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalCoroutinesApi
internal class RoomLocalFavouriteMoviesDataSource @Inject constructor(
    private val moviesDao: MoviesDao,
    private val dataStore: DataStore<Preferences>,
) : LocalFavouriteMoviesDataSource {

    override fun getFavouriteMovies(): Flow<List<Movie>> {
        return getFavouriteMoviesIds()
            .flatMapLatest { moviesIds ->
                moviesDao.getAndSortByIds(moviesIds).map { movies ->
                    movies.map { it.toDomain() }
                }
            }
    }

    override fun getFavouriteMoviesIds(): Flow<List<Long>> {
        return dataStore.data
            .map { it[stringSetPreferencesKey(FAVOURITE_MOVIES_IDS_KEY)] ?: emptySet() }
            .map { idsSet -> idsSet.map { it.toLong() } }
    }

    override suspend fun addToFavourites(id: Long) {
        updateFavourites { favouriteMoviesIds ->
            setOf(id.toString()) + favouriteMoviesIds
        }
    }

    override suspend fun removeFromFavourites(id: Long) {
        updateFavourites { favouriteMoviesIds ->
            favouriteMoviesIds - id.toString()
        }
    }

    private suspend fun updateFavourites(updateAction: (favouriteIds: Set<String>) -> Set<String>) {
        dataStore.edit { preferences ->
            val favouriteMoviesIds: Set<String> = preferences[
                    stringSetPreferencesKey(FAVOURITE_MOVIES_IDS_KEY)
            ] ?: emptySet()
            val updatedFavouriteMoviesIds = updateAction(favouriteMoviesIds)
            preferences[
                    stringSetPreferencesKey(FAVOURITE_MOVIES_IDS_KEY)
            ] = updatedFavouriteMoviesIds
        }
    }

    companion object {
        const val FAVOURITE_MOVIES_IDS_KEY = "favourite_movies_ids"
    }
}