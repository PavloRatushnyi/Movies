package com.pavelratushnyi.movies.data.movies.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class MovieGenreDto(
    @Json(name = "id")
    val id: Long,
    @Json(name = "name")
    val name: String
)