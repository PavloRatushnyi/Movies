package com.pavloratushnyi.movies.ui.screen.popularmovies

import com.pavloratushnyi.model.UserMovie
import com.pavloratushnyi.movies.domain.Resource

internal data class PopularMoviesUiState(
    val movies: Resource<List<UserMovie>> = Resource.Loading()
)