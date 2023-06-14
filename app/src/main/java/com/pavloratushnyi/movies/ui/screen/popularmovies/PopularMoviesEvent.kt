package com.pavloratushnyi.movies.ui.screen.popularmovies

import com.pavloratushnyi.movies.domain.vo.UserMovie

internal sealed class PopularMoviesEvent {

    object Refresh : PopularMoviesEvent()

    data class ToggleFavourite(val movie: UserMovie) : PopularMoviesEvent()
}