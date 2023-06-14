package com.pavloratushnyi.movies.ui.screen.moviedetails

import com.pavloratushnyi.model.MovieDetails
import com.pavloratushnyi.movies.domain.Resource

internal data class MovieDetailsUiState(
    val movieDetails: Resource<MovieDetails> = Resource.Loading()
)