package com.pavelratushnyi.movies.data.movies.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class MoviesPageDto(
    @Json(name = "results")
    val results: List<MovieDto>
)