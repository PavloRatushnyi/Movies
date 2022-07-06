package com.pavelratushnyi.movies.data.movies

import com.pavelratushnyi.movies.data.movies.remote.MovieDto
import com.pavelratushnyi.movies.domain.vo.Movie

internal fun MovieDto.toDomain() = Movie(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath
)