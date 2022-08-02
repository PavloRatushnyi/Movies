package com.pavelratushnyi.movies.data.moviedetails.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "movie_production_country_cross_ref",
    primaryKeys = ["movie_id", "country_iso_code"],
    indices = [
        Index(
            value = ["movie_id", "country_iso_code", "position"],
            name = "movie_country_index",
            unique = true
        )
    ]
)
internal data class MovieProductionCountryCrossRef(
    @ColumnInfo(name = "movie_id")
    val movieId: Long,
    @ColumnInfo(name = "country_iso_code")
    val countryIsoCode: String,
    @ColumnInfo(name = "position")
    val position: Int,
)