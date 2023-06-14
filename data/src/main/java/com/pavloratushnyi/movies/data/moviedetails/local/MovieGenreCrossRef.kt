package com.pavloratushnyi.movies.data.moviedetails.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "movie_genre_cross_ref",
    primaryKeys = ["movie_id", "genre_id"],
    indices = [
        Index(
            value = ["movie_id", "genre_id", "position"],
            name = "movie_genre_index",
            unique = true
        )
    ]
)
internal data class MovieGenreCrossRef(
    @ColumnInfo(name = "movie_id")
    val movieId: Long,
    @ColumnInfo(name = "genre_id")
    val genreId: Long,
    @ColumnInfo(name = "position")
    val position: Int,
)