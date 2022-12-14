package com.pavelratushnyi.movies.ui.screen.popularmovies

import com.pavelratushnyi.movies.domain.Resource
import com.pavelratushnyi.movies.domain.vo.UserMovie

internal data class PopularMoviesUiState(
    val movies: Resource<List<UserMovie>> = Resource.Loading()
)