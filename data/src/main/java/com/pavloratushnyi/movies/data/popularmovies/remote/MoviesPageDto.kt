package com.pavloratushnyi.movies.data.popularmovies.remote

import com.pavloratushnyi.movies.data.movies.remote.MovieDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class MoviesPageDto(
    @Json(name = "results")
    val results: List<MovieDto>
)