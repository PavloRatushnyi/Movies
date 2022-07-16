package com.pavelratushnyi.movies.ui.screen.favouritemovies

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavelratushnyi.movies.domain.usecase.GetFavouriteUserMoviesStreamUseCase
import com.pavelratushnyi.movies.domain.usecase.RemoveFromFavouritesUseCase
import com.pavelratushnyi.movies.domain.vo.UserMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FavouriteMoviesViewModel @Inject constructor(
    getFavouriteUserMoviesStreamUseCase: GetFavouriteUserMoviesStreamUseCase,
    private val removeFromFavouritesUseCase: RemoveFromFavouritesUseCase
) : ViewModel() {

    var uiState by mutableStateOf(FavouriteMoviesUiState())
        private set

    init {
        getFavouriteUserMoviesStreamUseCase()
            .onEach { movies -> uiState = uiState.copy(movies = movies) }
            .launchIn(viewModelScope)
    }

    fun toggleFavouriteClicked(movie: UserMovie) {
        if (movie.favourite.not()) return
        viewModelScope.launch {
            removeFromFavouritesUseCase(movie)
        }
    }
}