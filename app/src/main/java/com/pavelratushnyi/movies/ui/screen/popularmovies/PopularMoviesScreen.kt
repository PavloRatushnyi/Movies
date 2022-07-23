package com.pavelratushnyi.movies.ui.screen.popularmovies

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pavelratushnyi.movies.ui.sharedcomposables.MoviesList

@ExperimentalLifecycleComposeApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
internal fun PopularMoviesScreen(viewModel: PopularMoviesViewModel = viewModel()) {
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    MoviesList(
        moviesResource = uiState.movies,
        toggleFavoriteClicked = { viewModel.toggleFavouriteClicked(it) }
    )
}