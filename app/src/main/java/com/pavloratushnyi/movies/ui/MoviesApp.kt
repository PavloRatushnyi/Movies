package com.pavloratushnyi.movies.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.pavloratushnyi.movies.ui.theme.MoviesTheme

@Composable
internal fun MoviesApp() {
    MoviesTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val navController = rememberNavController()
            MoviesNavHost(
                navController = navController,
            )
        }
    }
}