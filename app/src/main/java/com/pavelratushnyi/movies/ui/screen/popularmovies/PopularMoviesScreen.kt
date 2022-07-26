package com.pavelratushnyi.movies.ui.screen.popularmovies

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.pavelratushnyi.movies.ui.sharedcomposables.MoviesList

@ExperimentalLifecycleComposeApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
internal fun PopularMoviesScreen(viewModel: PopularMoviesViewModel = viewModel()) {
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshingFlow.collectAsStateWithLifecycle()
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { viewModel.refresh() },
    ) {
        MoviesList(
            moviesResource = uiState.movies,
            toggleFavoriteClicked = { viewModel.toggleFavouriteClicked(it) },
            emptyContentModifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            errorContentModifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
        )
    }
}