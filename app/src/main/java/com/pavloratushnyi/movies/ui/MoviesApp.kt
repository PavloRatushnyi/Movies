package com.pavloratushnyi.movies.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.pavloratushnyi.movies.ui.screen.moviedetails.movieDetailsScreen
import com.pavloratushnyi.movies.ui.screen.moviedetails.navigateToMovieDetails
import com.pavloratushnyi.movies.ui.screen.movies.MoviesDestination
import com.pavloratushnyi.movies.ui.screen.movies.moviesScreen
import com.pavloratushnyi.movies.ui.theme.MoviesTheme

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
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