package com.pavelratushnyi.movies.data.moviedetails.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "movie_production_company_cross_ref",
    primaryKeys = ["movie_id", "production_company_id"],
    indices = [
        Index(
            value = ["movie_id", "production_company_id", "position"],
            name = "movie_company_index",
            unique = true
        )
    ]
)
internal data class MovieProductionCompanyCrossRef(
    @ColumnInfo(name = "movie_id")
    val movieId: Long,
    @ColumnInfo(name = "production_company_id")
    val productionCompanyId: Long,
    @ColumnInfo(name = "position")
    val position: Int,
)