package com.pavelratushnyi.movies.ui.screen.popularmovies

import com.pavelratushnyi.movies.domain.vo.UserMovie

internal sealed class PopularMoviesEvent {

    object Refresh : PopularMoviesEvent()

    data class ToggleFavourite(val movie: UserMovie) : PopularMoviesEvent()
}