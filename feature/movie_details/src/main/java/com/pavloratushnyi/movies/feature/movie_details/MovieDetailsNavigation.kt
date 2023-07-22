package com.pavloratushnyi.movies.feature.movie_details

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavController.navigateToMovieDetails(movieId: Long) {
    navigate(MovieDetailsDestination.createNavigationRoute(movieId))
}

fun NavGraphBuilder.movieDetailsScreen() {
    composable(
        route = MovieDetailsDestination.route,
        arguments = listOf(
            navArgument(MovieDetailsDestination.movieIdArg) { type = NavType.LongType }
        )
    ) {
        MovieDetailsScreen()
    }
}

internal object MovieDetailsDestination {

    const val movieIdArg = "movieId"
    const val route = "details/{$movieIdArg}"

    fun createNavigationRoute(movieIdArg: Long): String {
        return "details/$movieIdArg"
    }
}

internal class MovieDetailsArgs(val movieId: Long) {
    constructor(
        savedStateHandle: SavedStateHandle
    ) : this(checkNotNull(savedStateHandle.get<Long>(MovieDetailsDestination.movieIdArg)))
}