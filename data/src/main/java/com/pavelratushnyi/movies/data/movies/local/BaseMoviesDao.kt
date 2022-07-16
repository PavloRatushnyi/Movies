package com.pavelratushnyi.movies.data.movies.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

internal interface BaseMoviesDao {

    @Query("SELECT * FROM movies WHERE id IN (:movieIds)")
    fun getByIds(movieIds: LongArray): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg movies: MovieEntity)
}