package com.pavelratushnyi.movies.domain.usecase

import com.pavelratushnyi.movies.domain.repository.FavouriteMoviesRepository
import com.pavelratushnyi.movies.domain.vo.UserMovie
import javax.inject.Inject

class RemoveFromFavouritesUseCase @Inject constructor(
    private val favouriteMoviesRepository: FavouriteMoviesRepository
) {

    suspend operator fun invoke(movie: UserMovie) {
        if (movie.favourite.not()) return
        favouriteMoviesRepository.removeFromFavourites(movie.movie.id)
    }
}