package com.pavloratushnyi.movies.domain.usecase

import com.pavloratushnyi.movies.data.favouritemovies.FavouriteMoviesRepository
import com.pavloratushnyi.movies.model.UserMovie
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