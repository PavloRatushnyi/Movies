package com.pavelratushnyi.movies.data.popularmovies

import com.pavelratushnyi.movies.data.movies.local.MovieEntity
import com.pavelratushnyi.movies.data.movies.remote.MovieDto
import com.pavelratushnyi.movies.domain.vo.Movie

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