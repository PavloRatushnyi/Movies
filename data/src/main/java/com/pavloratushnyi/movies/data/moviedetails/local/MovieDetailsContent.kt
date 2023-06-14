package com.pavloratushnyi.movies.data.moviedetails.local

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

internal data class MovieDetailsContent(
    @Embedded
    val movieDetails: MovieDetailsEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = MovieGenreCrossRef::class,
            parentColumn = "movie_id",
            entityColumn = "genre_id"
        )
    )
    val genres: List<MovieGenreEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = MovieProductionCompanyCrossRef::class,
            parentColumn = "movie_id",
            entityColumn = "production_company_id"
        )
    )
    val productionCompanies: List<MovieProductionCompanyEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "iso_code",
        associateBy = Junction(
            value = MovieProductionCountryCrossRef::class,
            parentColumn = "movie_id",
            entityColumn = "country_iso_code"
        )
    )
    val productionCountries: List<MovieProductionCountryEntity>,
)