package com.pavloratushnyi.movies.feature.favourite_movies

import com.pavloratushnyi.movies.model.UserMovie
import com.pavloratushnyi.resource.Resource

data class FavouriteMoviesUiState(
    val movies: Resource<List<UserMovie>> = Resource.Loading()
)