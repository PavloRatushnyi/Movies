package com.pavloratushnyi.movies.ui.screen.favouritemovies

import com.pavloratushnyi.movies.domain.Resource
import com.pavloratushnyi.movies.domain.vo.UserMovie

internal data class FavouriteMoviesUiState(
    val movies: Resource<List<UserMovie>> = Resource.Loading()
)