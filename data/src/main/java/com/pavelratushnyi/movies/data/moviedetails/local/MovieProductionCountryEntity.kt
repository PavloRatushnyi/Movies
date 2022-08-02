package com.pavelratushnyi.movies.data.moviedetails.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_production_country")
internal data class MovieProductionCountryEntity(
    @PrimaryKey
    @ColumnInfo(name = "iso_code")
    val isoCode: String,
    @ColumnInfo(name = "name")
    val name: String
)