package com.pavloratushnyi.movies.feature.popular_movies

import com.pavloratushnyi.movies.model.UserMovie
import com.pavloratushnyi.resource.Resource

data class PopularMoviesUiState(
    val movies: Resource<List<UserMovie>> = Resource.Loading()
)