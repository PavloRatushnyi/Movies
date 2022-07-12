package com.pavelratushnyi.movies.data.movies.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
internal interface MoviesDao {

    @Query("SELECT * FROM movies WHERE id IN (:movieIds)")
    suspend fun loadByIds(movieIds: LongArray): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg movies: MovieEntity)
}