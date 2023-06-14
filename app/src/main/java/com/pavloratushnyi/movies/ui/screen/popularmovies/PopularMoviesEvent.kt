package com.pavloratushnyi.movies.ui.screen.popularmovies

import com.pavloratushnyi.movies.model.UserMovie

internal sealed class PopularMoviesEvent {

    object Refresh : PopularMoviesEvent()

    data class ToggleFavourite(val movie: UserMovie) : PopularMoviesEvent()
}