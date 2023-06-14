package com.pavloratushnyi.movies.data.moviedetails.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class MovieDetailsDto(
    @Json(name = "id")
    val id: Long,
    @Json(name = "title")
    val title: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "genres")
    val genres: List<MovieGenreDto>,
    @Json(name = "production_companies")
    val productionCompanies: List<MovieProductionCompanyDto>,
    @Json(name = "production_countries")
    val productionCountries: List<MovieProductionCountryDto>
)