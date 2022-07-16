package com.pavelratushnyi.movies.domain.usecase

import com.pavelratushnyi.movies.domain.repository.MoviesRepository
import com.pavelratushnyi.movies.domain.vo.UserMovie
import javax.inject.Inject

class ToggleFavouriteUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    suspend operator fun invoke(movie: UserMovie) {
        if (movie.favourite) {
            moviesRepository.removeFromFavourites(movie.movie.id)
        } else {
            moviesRepository.addToFavourites(movie.movie.id)
        }
    }
}