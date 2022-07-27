package com.pavelratushnyi.movies.ui.screen.moviedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavelratushnyi.movies.domain.usecase.GetMovieDetailsStreamUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class MovieDetailsViewModel @Inject constructor(
    getMovieDetailsStreamUseCase: GetMovieDetailsStreamUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId = savedStateHandle.get<Long>("movieId")!!

    val uiStateFlow = getMovieDetailsStreamUseCase(movieId)
        .scan(MovieDetailsUiState()) { uiState, movieDetails ->
            uiState.copy(movieDetails = movieDetails)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MovieDetailsUiState()
        )
}