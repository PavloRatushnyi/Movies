package com.pavloratushnyi.movies.ui.screen.moviedetails

import com.pavloratushnyi.movies.domain.Resource
import com.pavloratushnyi.movies.domain.vo.MovieDetails

internal data class MovieDetailsUiState(
    val movieDetails: Resource<MovieDetails> = Resource.Loading()
)