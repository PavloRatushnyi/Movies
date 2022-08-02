package com.pavelratushnyi.movies.ui.screen.favouritemovies

import com.pavelratushnyi.movies.domain.vo.UserMovie

internal sealed class FavouriteMoviesEvent {

    data class ToggleFavourite(val movie: UserMovie) : FavouriteMoviesEvent()
}