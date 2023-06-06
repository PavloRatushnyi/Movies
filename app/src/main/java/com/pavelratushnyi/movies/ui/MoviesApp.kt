package com.pavelratushnyi.movies.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.pavelratushnyi.movies.ui.screen.moviedetails.MovieDetailsDestination
import com.pavelratushnyi.movies.ui.screen.moviedetails.MovieDetailsScreen
import com.pavelratushnyi.movies.ui.screen.movies.MoviesDestination
import com.pavelratushnyi.movies.ui.screen.movies.MoviesScreen
import com.pavelratushnyi.movies.ui.theme.MoviesTheme

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
internal fun MoviesApp() {
    MoviesTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = MoviesDestination.route) {
                composable(MoviesDestination.route) {
                    MoviesScreen(onMovieClicked = { movie ->
                        navController.navigate(MovieDetailsDestination.createNavigationRoute(movie.id))
                    })
                }
                composable(
                    MovieDetailsDestination.route,
                    arguments = listOf(
                        navArgument(MovieDetailsDestination.movieIdArg) { type = NavType.LongType }
                    )
                ) {
                    MovieDetailsScreen()
                }
            }
        }
    }
}