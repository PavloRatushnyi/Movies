package com.pavelratushnyi.movies.ui.screen.movies

import com.pavelratushnyi.movies.data.Resource
import com.pavelratushnyi.movies.domain.vo.Movie

data class MoviesUiState(
    val movies: Resource<List<Movie>> = Resource.Loading()
)