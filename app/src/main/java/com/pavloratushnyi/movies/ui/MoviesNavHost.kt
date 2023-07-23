package com.pavloratushnyi.movies.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.pavloratushnyi.movies.feature.movie_details.movieDetailsScreen
import com.pavloratushnyi.movies.feature.movie_details.navigateToMovieDetails
import com.pavloratushnyi.movies.feature.movies.MOVIES_DESTINATION_ROUTE
import com.pavloratushnyi.movies.feature.movies.moviesScreen

@Composable
internal fun MoviesNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = MOVIES_DESTINATION_ROUTE
    ) {
        moviesScreen(onMovieClicked = { movieId ->
            navController.navigateToMovieDetails(movieId)
        })
        movieDetailsScreen()
    }
}