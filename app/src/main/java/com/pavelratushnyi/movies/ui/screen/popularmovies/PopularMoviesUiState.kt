package com.pavelratushnyi.movies.ui.screen.popularmovies

import com.pavelratushnyi.movies.data.Resource
import com.pavelratushnyi.movies.domain.vo.Movie

data class PopularMoviesUiState(
    val movies: Resource<List<Movie>> = Resource.Loading()
)