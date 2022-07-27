package com.pavelratushnyi.movies.ui.screen.favouritemovies

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pavelratushnyi.movies.domain.vo.Movie
import com.pavelratushnyi.movies.ui.sharedcomposables.MoviesList

@ExperimentalLifecycleComposeApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
internal fun FavouriteMoviesScreen(
    viewModel: FavouriteMoviesViewModel = hiltViewModel(),
    onMovieClicked: (Movie) -> Unit
) {
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    MoviesList(
        moviesResource = uiState.movies,
        onToggleFavoriteClicked = { viewModel.toggleFavouriteClicked(it) },
        onMovieClicked = onMovieClicked
    )
}