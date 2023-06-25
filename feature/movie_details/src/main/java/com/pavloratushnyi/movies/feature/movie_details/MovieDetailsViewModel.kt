package com.pavloratushnyi.movies.feature.movie_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavloratushnyi.movies.domain.usecase.GetMovieDetailsStreamUseCase
import com.pavloratushnyi.movies.domain.usecase.RefreshMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    getMovieDetailsStreamUseCase: GetMovieDetailsStreamUseCase,
    private val refreshMovieDetailsUseCase: RefreshMovieDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: Long = checkNotNull(savedStateHandle[MovieDetailsDestination.movieIdArg])

    private val _isRefreshingFlow = MutableStateFlow(false)
    val isRefreshingFlow: StateFlow<Boolean> = _isRefreshingFlow.asStateFlow()

    val uiStateFlow = getMovieDetailsStreamUseCase(movieId)
        .scan(MovieDetailsUiState()) { uiState, movieDetails ->
            uiState.copy(movieDetails = movieDetails)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MovieDetailsUiState()
        )

    fun onEvent(event: MovieDetailsEvent) {
        when (event) {
            MovieDetailsEvent.Refresh -> refresh()
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _isRefreshingFlow.emit(true)
            refreshMovieDetailsUseCase(movieId)
            _isRefreshingFlow.emit(false)
        }
    }
}