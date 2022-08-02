package com.pavelratushnyi.movies.ui.screen.popularmovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavelratushnyi.movies.domain.usecase.GetPopularUserMoviesStreamUseCase
import com.pavelratushnyi.movies.domain.usecase.RefreshPopularMoviesUseCase
import com.pavelratushnyi.movies.domain.usecase.ToggleFavouriteUseCase
import com.pavelratushnyi.movies.domain.vo.UserMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PopularMoviesViewModel @Inject constructor(
    getPopularUserMoviesStreamUseCase: GetPopularUserMoviesStreamUseCase,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase,
    private val refreshPopularMoviesUseCase: RefreshPopularMoviesUseCase
) : ViewModel() {

    val uiStateFlow = getPopularUserMoviesStreamUseCase()
        .scan(PopularMoviesUiState()) { uiState, movies ->
            uiState.copy(movies = movies)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PopularMoviesUiState()
        )

    private val _isRefreshingFlow = MutableStateFlow(false)
    val isRefreshingFlow: StateFlow<Boolean> = _isRefreshingFlow.asStateFlow()

    fun onEvent(event: PopularMoviesEvent) {
        when (event) {
            PopularMoviesEvent.Refresh -> refresh()
            is PopularMoviesEvent.ToggleFavourite -> toggleFavourite(event.movie)
        }
    }

    private fun toggleFavourite(movie: UserMovie) {
        viewModelScope.launch {
            toggleFavouriteUseCase(movie)
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _isRefreshingFlow.emit(true)
            refreshPopularMoviesUseCase()
            _isRefreshingFlow.emit(false)
        }
    }
}