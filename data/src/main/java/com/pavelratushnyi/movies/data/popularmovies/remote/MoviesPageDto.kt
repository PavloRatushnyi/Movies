package com.pavelratushnyi.movies.data.popularmovies.remote

import com.pavelratushnyi.movies.data.movies.remote.MovieDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class MoviesPageDto(
    @Json(name = "results")
    val results: List<MovieDto>
)