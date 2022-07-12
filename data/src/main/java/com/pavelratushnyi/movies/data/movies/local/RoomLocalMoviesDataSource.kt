package com.pavelratushnyi.movies.data.movies.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.pavelratushnyi.movies.data.movies.toDomain
import com.pavelratushnyi.movies.data.movies.toEntity
import com.pavelratushnyi.movies.domain.vo.Movie
import kotlinx.coroutines.flow.first
import javax.inject.Inject

internal class RoomLocalMoviesDataSource @Inject constructor(
    private val moviesDao: MoviesDao,
    private val dataStore: DataStore<Preferences>
) : LocalMoviesDataSource {

    override suspend fun getPopularMovies(): List<Movie>? {
        return dataStore.data.first()[stringSetPreferencesKey(POPULAR_MOVIES_IDS_KEY)]
            ?.map { it.toLong() }
            ?.let { popularMoviesIds ->
                val popularMoviesIdsPositionMap = popularMoviesIds.withIndex().associate {
                    it.value to it.index
                }
                moviesDao.loadByIds(popularMoviesIds.toLongArray()).map {
                    it.toDomain()
                }.sortedBy { popularMoviesIdsPositionMap.getValue(it.id) }
            }
    }

    override suspend fun insertPopularMovies(movies: List<Movie>) {
        dataStore.edit { preferences ->
            preferences[stringSetPreferencesKey(POPULAR_MOVIES_IDS_KEY)] = movies.map {
                it.id.toString()
            }.toSet()
        }
        moviesDao.insert(*movies.map { it.toEntity() }.toTypedArray())
    }

    companion object {
        const val POPULAR_MOVIES_IDS_KEY = "popular_movies_ids"
    }
}