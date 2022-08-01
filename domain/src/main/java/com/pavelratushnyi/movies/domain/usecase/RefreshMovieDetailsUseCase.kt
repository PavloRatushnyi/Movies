package com.pavelratushnyi.movies.domain.usecase

import com.pavelratushnyi.movies.domain.repository.MoviesRepository
import javax.inject.Inject

class RefreshMovieDetailsUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    suspend operator fun invoke(movieId: Long): Result<Unit> {
        return moviesRepository.refreshMovieDetails(movieId)
    }
}