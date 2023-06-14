package com.pavloratushnyi.movies.model

data class Movie(
    val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String?
)