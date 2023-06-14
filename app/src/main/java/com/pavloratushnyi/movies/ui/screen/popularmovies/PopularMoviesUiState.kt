package com.pavloratushnyi.movies.ui.screen.popularmovies

import com.pavloratushnyi.movies.domain.Resource
import com.pavloratushnyi.movies.domain.vo.UserMovie

internal data class PopularMoviesUiState(
    val movies: Resource<List<UserMovie>> = Resource.Loading()
)