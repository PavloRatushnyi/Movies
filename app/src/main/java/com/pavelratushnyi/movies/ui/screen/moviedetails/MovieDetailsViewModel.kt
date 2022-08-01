package com.pavelratushnyi.movies.ui.screen.moviedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavelratushnyi.movies.domain.usecase.GetMovieDetailsStreamUseCase
import com.pavelratushnyi.movies.domain.usecase.RefreshMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MovieDetailsViewModel @Inject constructor(
    getMovieDetailsStreamUseCase: GetMovieDetailsStreamUseCase,
    private val refreshMovieDetailsUseCase: RefreshMovieDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId = savedStateHandle.get<Long>("movieId")!!

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

    fun refresh() {
        viewModelScope.launch {
            _isRefreshingFlow.emit(true)
            refreshMovieDetailsUseCase(movieId)
            _isRefreshingFlow.emit(false)
        }
    }
}