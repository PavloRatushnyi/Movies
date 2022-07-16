package com.pavelratushnyi.movies.domain.usecase

import com.pavelratushnyi.movies.domain.repository.MoviesRepository
import com.pavelratushnyi.movies.domain.vo.UserMovie
import javax.inject.Inject

class RemoveFromFavouritesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    suspend operator fun invoke(movie: UserMovie) {
        if (movie.favourite.not()) return
        moviesRepository.removeFromFavourites(movie.movie.id)
    }
}