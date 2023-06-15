package com.pavloratushnyi.movies.ui.screen.movies

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pavloratushnyi.movies.model.Movie

object MoviesDestination {
    const val route = "movies"
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.moviesScreen(
    onMovieClicked: (Movie) -> Unit,
) {
    composable(
        route = MoviesDestination.route
    ) {
        MoviesScreen(onMovieClicked = onMovieClicked)
    }
}