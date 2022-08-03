package com.pavelratushnyi.movies.domain.usecase

import com.pavelratushnyi.movies.domain.repository.FavouriteMoviesRepository
import com.pavelratushnyi.movies.domain.vo.UserMovie
import javax.inject.Inject

class ToggleFavouriteUseCase @Inject constructor(
    private val favouriteMoviesRepository: FavouriteMoviesRepository
) {

    suspend operator fun invoke(movie: UserMovie) {
        if (movie.favourite) {
            favouriteMoviesRepository.removeFromFavourites(movie.movie.id)
        } else {
            favouriteMoviesRepository.addToFavourites(movie.movie.id)
        }
    }
}