package com.pavloratushnyi.movies.data.popularmovies

import com.pavloratushnyi.model.Movie
import com.pavloratushnyi.movies.data.movies.local.MovieEntity
import com.pavloratushnyi.movies.data.movies.remote.MovieDto

internal fun MovieDto.toDomain() = Movie(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath
)

internal fun MovieEntity.toDomain() = Movie(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath
)

internal fun Movie.toEntity() = MovieEntity(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath
)