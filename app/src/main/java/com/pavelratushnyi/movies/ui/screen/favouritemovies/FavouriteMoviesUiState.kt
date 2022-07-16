package com.pavelratushnyi.movies.ui.screen.favouritemovies

import com.pavelratushnyi.movies.domain.Resource
import com.pavelratushnyi.movies.domain.vo.UserMovie

internal data class FavouriteMoviesUiState(
    val movies: Resource<List<UserMovie>> = Resource.Loading()
)