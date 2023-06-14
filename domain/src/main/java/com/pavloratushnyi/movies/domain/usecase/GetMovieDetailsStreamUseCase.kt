package com.pavloratushnyi.movies.domain.usecase

import com.pavloratushnyi.model.MovieDetails
import com.pavloratushnyi.movies.domain.Resource
import com.pavloratushnyi.movies.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailsStreamUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
) {

    operator fun invoke(movieId: Long): Flow<Resource<MovieDetails>> {
        return movieDetailsRepository.getMovieDetails(movieId)
    }
}