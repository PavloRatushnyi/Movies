package com.pavloratushnyi.movies.feature.favourite_movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavloratushnyi.movies.domain.usecase.GetFavouriteUserMoviesStreamUseCase
import com.pavloratushnyi.movies.domain.usecase.RemoveFromFavouritesUseCase
import com.pavloratushnyi.movies.model.UserMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteMoviesViewModel @Inject constructor(
    getFavouriteUserMoviesStreamUseCase: GetFavouriteUserMoviesStreamUseCase,
    private val removeFromFavouritesUseCase: RemoveFromFavouritesUseCase
) : ViewModel() {

    val uiStateFlow = getFavouriteUserMoviesStreamUseCase()
        .scan(FavouriteMoviesUiState()) { uiState, movies ->
            uiState.copy(movies = movies)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FavouriteMoviesUiState()
        )

    fun onEvent(event: FavouriteMoviesEvent) {
        when (event) {
            is FavouriteMoviesEvent.ToggleFavourite -> toggleFavourite(event.movie)
        }
    }

    private fun toggleFavourite(movie: UserMovie) {
        if (movie.favourite.not()) return
        viewModelScope.launch {
            removeFromFavouritesUseCase(movie)
        }
    }
}