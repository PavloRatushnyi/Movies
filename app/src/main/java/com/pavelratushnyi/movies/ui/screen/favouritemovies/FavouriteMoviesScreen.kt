package com.pavelratushnyi.movies.ui.screen.favouritemovies

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pavelratushnyi.movies.ui.sharedcomposables.MoviesList

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
internal fun FavouriteMoviesScreen(viewModel: FavouriteMoviesViewModel = viewModel()) {
    MoviesList(
        moviesResource = viewModel.uiState.movies,
        toggleFavoriteClicked = { viewModel.toggleFavouriteClicked(it) }
    )
}