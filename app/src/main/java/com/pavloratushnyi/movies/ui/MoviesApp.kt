package com.pavloratushnyi.movies.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.pavloratushnyi.movies.feature.movie_details.movieDetailsScreen
import com.pavloratushnyi.movies.feature.movie_details.navigateToMovieDetails
import com.pavloratushnyi.movies.feature.movies.MoviesDestination
import com.pavloratushnyi.movies.feature.movies.moviesScreen
import com.pavloratushnyi.movies.ui.theme.MoviesTheme

@Composable
internal fun MoviesApp() {
    MoviesTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = MoviesDestination.route
            ) {
                moviesScreen(onMovieClicked = { movie ->
                    navController.navigateToMovieDetails(movie.id)
                })
                movieDetailsScreen()
            }
        }
    }
}