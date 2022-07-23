package com.pavelratushnyi.movies.ui.screen.popularmovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavelratushnyi.movies.domain.usecase.GetPopularUserMoviesStreamUseCase
import com.pavelratushnyi.movies.domain.usecase.ToggleFavouriteUseCase
import com.pavelratushnyi.movies.domain.vo.UserMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PopularMoviesViewModel @Inject constructor(
    getPopularUserMoviesStreamUseCase: GetPopularUserMoviesStreamUseCase,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase
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

    fun toggleFavouriteClicked(movie: UserMovie) {
        viewModelScope.launch {
            toggleFavouriteUseCase(movie)
        }
    }
}