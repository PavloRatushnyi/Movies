package com.pavloratushnyi.movies.data.moviedetails.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class MovieProductionCountryDto(
    @Json(name = "name")
    val name: String,
    @Json(name = "iso_3166_1")
    val isoCode: String
)