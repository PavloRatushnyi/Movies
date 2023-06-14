package com.pavloratushnyi.movies.ui.screen.moviedetails

import com.pavloratushnyi.movies.model.MovieDetails
import com.pavloratushnyi.resource.Resource

internal data class MovieDetailsUiState(
    val movieDetails: Resource<MovieDetails> = Resource.Loading()
)