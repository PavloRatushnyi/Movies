package com.pavelratushnyi.movies.ui.screen.favouritemovies

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pavelratushnyi.movies.domain.vo.Movie
import com.pavelratushnyi.movies.ui.sharedcomposables.MoviesList

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
        onToggleFavoriteClicked = { viewModel.onEvent(FavouriteMoviesEvent.ToggleFavourite(it)) },
        emptyContentModifier = Modifier
            .fillMaxSize(),
        errorContentModifier = Modifier
            .fillMaxSize(),
        onMovieClicked = onMovieClicked
    )
}