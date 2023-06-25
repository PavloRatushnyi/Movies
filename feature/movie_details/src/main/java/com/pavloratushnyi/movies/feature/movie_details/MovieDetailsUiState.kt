package com.pavloratushnyi.movies.feature.movie_details

import com.pavloratushnyi.movies.model.MovieDetails
import com.pavloratushnyi.resource.Resource

data class MovieDetailsUiState(
    val movieDetails: Resource<MovieDetails> = Resource.Loading()
)