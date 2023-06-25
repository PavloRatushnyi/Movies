package com.pavloratushnyi.movies.feature.movie_details

sealed class MovieDetailsEvent {

    object Refresh : MovieDetailsEvent()
}