package com.pavloratushnyi.movies.feature.favourite_movies

import com.pavloratushnyi.movies.model.UserMovie

sealed class FavouriteMoviesEvent {

    data class ToggleFavourite(val movie: UserMovie) : FavouriteMoviesEvent()
}