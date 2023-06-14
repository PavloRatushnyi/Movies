package com.pavloratushnyi.movies.ui.screen.favouritemovies

import com.pavloratushnyi.movies.model.UserMovie
import com.pavloratushnyi.resource.Resource

internal data class FavouriteMoviesUiState(
    val movies: Resource<List<UserMovie>> = Resource.Loading()
)