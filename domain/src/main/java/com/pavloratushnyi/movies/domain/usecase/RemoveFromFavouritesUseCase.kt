package com.pavloratushnyi.movies.domain.usecase

import com.pavloratushnyi.movies.data.favouritemovies.FavouriteMoviesRepository
import com.pavloratushnyi.movies.model.UserMovie
import javax.inject.Inject

class RemoveFromFavouritesUseCase @Inject constructor(
    private val favouriteMoviesRepository: FavouriteMoviesRepository
) {

    suspend operator fun invoke(movie: UserMovie) {
        if (movie.favourite.not()) return
        favouriteMoviesRepository.removeFromFavourites(movie.movie.id)
    }
}