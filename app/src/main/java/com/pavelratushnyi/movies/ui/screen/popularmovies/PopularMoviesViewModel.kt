package com.pavelratushnyi.movies.ui.screen.popularmovies

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavelratushnyi.movies.domain.usecase.GetPopularUserMoviesStreamUseCase
import com.pavelratushnyi.movies.domain.usecase.ToggleFavouriteUseCase
import com.pavelratushnyi.movies.domain.vo.UserMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PopularMoviesViewModel @Inject constructor(
    getPopularUserMoviesStreamUseCase: GetPopularUserMoviesStreamUseCase,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase
) : ViewModel() {

    var uiState by mutableStateOf(PopularMoviesUiState())
        private set

    init {
        getPopularUserMoviesStreamUseCase()
            .onEach { movies -> uiState = uiState.copy(movies = movies) }
            .launchIn(viewModelScope)
    }

    fun toggleFavouriteClicked(movie: UserMovie) {
        viewModelScope.launch {
            toggleFavouriteUseCase(movie)
        }
    }
}