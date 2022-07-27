package com.pavelratushnyi.movies.ui.screen.popularmovies

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.pavelratushnyi.movies.domain.vo.Movie
import com.pavelratushnyi.movies.ui.sharedcomposables.MoviesList

@ExperimentalLifecycleComposeApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
internal fun PopularMoviesScreen(
    viewModel: PopularMoviesViewModel = hiltViewModel(),
    onMovieClicked: (Movie) -> Unit
) {
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshingFlow.collectAsStateWithLifecycle()
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { viewModel.refresh() },
    ) {
        MoviesList(
            moviesResource = uiState.movies,
            onToggleFavoriteClicked = { viewModel.toggleFavouriteClicked(it) },
            emptyContentModifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            errorContentModifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            onMovieClicked = onMovieClicked
        )
    }
}