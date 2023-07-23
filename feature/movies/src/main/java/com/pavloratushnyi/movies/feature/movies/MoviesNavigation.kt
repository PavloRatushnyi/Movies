package com.pavloratushnyi.movies.feature.movies

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val MOVIES_DESTINATION_ROUTE = MoviesDestination.route

fun NavGraphBuilder.moviesScreen(
    onMovieClicked: (Long) -> Unit,
) {
    composable(
        route = MoviesDestination.route
    ) {
        MoviesScreen(onMovieClicked = onMovieClicked)
    }
}

internal object MoviesDestination {
    const val route = "movies"
}
