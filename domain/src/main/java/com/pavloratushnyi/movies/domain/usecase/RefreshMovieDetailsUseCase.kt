package com.pavloratushnyi.movies.domain.usecase

import com.pavloratushnyi.movies.data.moviedetails.MovieDetailsRepository
import javax.inject.Inject

class RefreshMovieDetailsUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
) {

    suspend operator fun invoke(movieId: Long): Result<Unit> {
        return movieDetailsRepository.refreshMovieDetails(movieId)
    }
}