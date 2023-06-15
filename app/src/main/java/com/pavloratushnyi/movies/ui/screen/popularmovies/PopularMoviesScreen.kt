package com.pavloratushnyi.movies.ui.screen.popularmovies

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
import com.pavloratushnyi.movies.model.Movie
import com.pavloratushnyi.movies.ui.sharedcomposables.MoviesList

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun PopularMoviesScreen(
    viewModel: PopularMoviesViewModel = hiltViewModel(),
    onMovieClicked: (Movie) -> Unit
) {
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshingFlow.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.onEvent(PopularMoviesEvent.Refresh) })
    Box(Modifier.pullRefresh(pullRefreshState)) {
        MoviesList(
            moviesResource = uiState.movies,
            onToggleFavoriteClicked = { viewModel.onEvent(PopularMoviesEvent.ToggleFavourite(it)) },
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