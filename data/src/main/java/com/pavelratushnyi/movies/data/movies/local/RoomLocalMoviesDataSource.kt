package com.pavelratushnyi.movies.data.movies.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.pavelratushnyi.movies.data.movies.toDomain
import com.pavelratushnyi.movies.data.movies.toEntity
import com.pavelratushnyi.movies.domain.vo.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalCoroutinesApi
internal class RoomLocalMoviesDataSource @Inject constructor(
    private val moviesDao: MoviesDao,
    private val dataStore: DataStore<Preferences>,
) : LocalMoviesDataSource {

    override fun getPopularMovies(): Flow<List<Movie>?> {
        return dataStore.data
            .map { it[stringSetPreferencesKey(POPULAR_MOVIES_IDS_KEY)] }
            .map { idsSet -> idsSet?.map { it.toLong() } }
            .flatMapLatest { moviesIds ->
                if (moviesIds == null) {
                    flowOf(null)
                } else {
                    moviesDao.getAndSortByIds(moviesIds)
                        .map { movies -> movies.map { it.toDomain() } }
                }
            }
    }

    override suspend fun insertPopularMovies(movies: List<Movie>) {
        dataStore.edit { preferences ->
            preferences[stringSetPreferencesKey(POPULAR_MOVIES_IDS_KEY)] = movies.map {
                it.id.toString()
            }.toSet()
        }
        moviesDao.insert(movies.map { it.toEntity() })
    }

    companion object {
        const val POPULAR_MOVIES_IDS_KEY = "popular_movies_ids"
    }
}