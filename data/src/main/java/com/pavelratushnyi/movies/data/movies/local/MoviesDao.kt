package com.pavelratushnyi.movies.data.movies.local

import androidx.room.Dao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Dao
internal interface MoviesDao : BaseMoviesDao {

    fun getAndSortByIds(movieIds: LongArray): Flow<List<MovieEntity>> {
        val moviesIdsPositionMap = movieIds.withIndex().associate {
            it.value to it.index
        }
        return getByIds(movieIds).map { movies -> movies.sortedBy { moviesIdsPositionMap.getValue(it.id) } }
    }
}