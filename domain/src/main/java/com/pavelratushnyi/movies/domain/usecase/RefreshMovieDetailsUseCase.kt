package com.pavelratushnyi.movies.domain.usecase

import com.pavelratushnyi.movies.domain.repository.MovieDetailsRepository
import javax.inject.Inject

class RefreshMovieDetailsUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
) {

    suspend operator fun invoke(movieId: Long): Result<Unit> {
        return movieDetailsRepository.refreshMovieDetails(movieId)
    }
}