package com.pavelratushnyi.movies.ui.screen.movies

import com.pavelratushnyi.movies.domain.vo.Movie

data class MoviesUiState(
    val movies: List<Movie> = emptyList()
)