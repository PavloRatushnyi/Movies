package com.pavloratushnyi.movies.feature.popular_movies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pavloratushnyi.movies.model.UserMovie
import com.pavloratushnyi.movies.shared_composables.MoviesList

@Composable
fun PopularMoviesScreen(
    viewModel: PopularMoviesViewModel = hiltViewModel(),
    onMovieClicked: (Long) -> Unit
) {
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshingFlow.collectAsStateWithLifecycle()
    PopularMoviesScreen(
        uiState = uiState,
        isRefreshing = isRefreshing,
        onMovieClicked = onMovieClicked,
        onToggleFavoriteClicked = { viewModel.onEvent(PopularMoviesEvent.ToggleFavourite(it)) },
        onRefresh = { viewModel.onEvent(PopularMoviesEvent.Refresh) },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PopularMoviesScreen(
    uiState: PopularMoviesUiState,
    isRefreshing: Boolean,
    onMovieClicked: (Long) -> Unit,
    onToggleFavoriteClicked: (UserMovie) -> Unit,
    onRefresh: () -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = onRefresh
    )
    Box(Modifier.pullRefresh(pullRefreshState)) {
        MoviesList(
            moviesResource = uiState.movies,
            onToggleFavoriteClicked = onToggleFavoriteClicked,
            emptyContentModifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            errorContentModifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            onMovieClicked = onMovieClicked
        )
        PullRefreshIndicator(isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}