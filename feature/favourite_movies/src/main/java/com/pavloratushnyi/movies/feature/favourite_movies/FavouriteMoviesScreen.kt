package com.pavloratushnyi.movies.feature.favourite_movies

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pavloratushnyi.movies.model.Movie
import com.pavloratushnyi.movies.model.UserMovie
import com.pavloratushnyi.movies.shared_composables.MoviesList

@Composable
fun FavouriteMoviesScreen(
    viewModel: FavouriteMoviesViewModel = hiltViewModel(),
    onMovieClicked: (Movie) -> Unit
) {
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    FavouriteMoviesScreen(
        uiState = uiState,
        onMovieClicked = onMovieClicked,
        onToggleFavoriteClicked = { viewModel.onEvent(FavouriteMoviesEvent.ToggleFavourite(it)) },
    )
}

@Composable
fun FavouriteMoviesScreen(
    uiState: FavouriteMoviesUiState,
    onMovieClicked: (Movie) -> Unit,
    onToggleFavoriteClicked: (UserMovie) -> Unit,
) {
    MoviesList(
        moviesResource = uiState.movies,
        onToggleFavoriteClicked = onToggleFavoriteClicked,
        emptyContentModifier = Modifier
            .fillMaxSize(),
        errorContentModifier = Modifier
            .fillMaxSize(),
        onMovieClicked = onMovieClicked,
    )
}