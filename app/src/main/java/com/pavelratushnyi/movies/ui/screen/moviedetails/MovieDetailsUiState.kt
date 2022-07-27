package com.pavelratushnyi.movies.ui.screen.moviedetails

import com.pavelratushnyi.movies.domain.Resource
import com.pavelratushnyi.movies.domain.vo.MovieDetails

internal data class MovieDetailsUiState(
    val movieDetails: Resource<MovieDetails> = Resource.Loading()
)