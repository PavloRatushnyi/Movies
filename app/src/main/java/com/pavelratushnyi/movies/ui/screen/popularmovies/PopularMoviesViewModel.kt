package com.pavelratushnyi.movies.ui.screen.popularmovies

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavelratushnyi.movies.data.movies.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    var uiState by mutableStateOf(PopularMoviesUiState())
        private set

    init {
        viewModelScope.launch {
            moviesRepository.getPopularMovies().collect { movies ->
                uiState = uiState.copy(movies = movies)
            }
        }
    }
}