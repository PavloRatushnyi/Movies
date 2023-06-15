package com.pavloratushnyi.movies.ui.screen.movies

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pavloratushnyi.movies.model.Movie

object MoviesDestination {
    const val route = "movies"
}

fun NavGraphBuilder.moviesScreen(
    onMovieClicked: (Movie) -> Unit,
) {
    composable(
        route = MoviesDestination.route
    ) {
        MoviesScreen(onMovieClicked = onMovieClicked)
    }
}