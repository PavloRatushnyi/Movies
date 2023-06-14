package com.pavloratushnyi.movies.ui.screen.favouritemovies

import com.pavloratushnyi.model.UserMovie
import com.pavloratushnyi.movies.domain.Resource

internal data class FavouriteMoviesUiState(
    val movies: Resource<List<UserMovie>> = Resource.Loading()
)