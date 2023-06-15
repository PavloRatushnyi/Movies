package com.pavloratushnyi.movies.ui.screen.moviedetails

import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

object MovieDetailsDestination {

    const val movieIdArg = "movieId"
    const val route = "details/{$movieIdArg}"

    fun createNavigationRoute(movieIdArg: Long): String {
        return "details/$movieIdArg"
    }
}

fun NavController.navigateToMovieDetails(movieId: Long) {
    navigate(MovieDetailsDestination.createNavigationRoute(movieId))
}

@ExperimentalMaterialApi
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