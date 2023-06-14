package com.pavloratushnyi.movies.ui.screen.favouritemovies

import com.pavloratushnyi.movies.model.UserMovie

internal sealed class FavouriteMoviesEvent {

    data class ToggleFavourite(val movie: UserMovie) : FavouriteMoviesEvent()
}