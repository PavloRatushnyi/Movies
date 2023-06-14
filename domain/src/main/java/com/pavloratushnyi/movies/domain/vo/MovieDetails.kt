package com.pavloratushnyi.movies.domain.vo

data class MovieDetails(
    val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val genres: List<MovieGenre>,
    val productionCompanies: List<MovieProductionCompany>,
    val productionCountries: List<MovieProductionCountry>,
)