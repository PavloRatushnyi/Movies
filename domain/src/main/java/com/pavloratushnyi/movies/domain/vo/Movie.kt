package com.pavloratushnyi.movies.domain.vo

data class Movie(
    val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String?
)