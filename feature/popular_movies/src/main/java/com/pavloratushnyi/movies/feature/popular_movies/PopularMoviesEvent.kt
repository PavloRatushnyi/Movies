package com.pavloratushnyi.movies.feature.popular_movies

import com.pavloratushnyi.movies.model.UserMovie

sealed class PopularMoviesEvent {

    object Refresh : PopularMoviesEvent()

    data class ToggleFavourite(val movie: UserMovie) : PopularMoviesEvent()
}