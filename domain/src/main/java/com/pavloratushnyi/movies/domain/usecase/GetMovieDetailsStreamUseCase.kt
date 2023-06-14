package com.pavloratushnyi.movies.domain.usecase

import com.pavloratushnyi.movies.data.moviedetails.MovieDetailsRepository
import com.pavloratushnyi.movies.model.MovieDetails
import com.pavloratushnyi.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailsStreamUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
) {

    operator fun invoke(movieId: Long): Flow<Resource<MovieDetails>> {
        return movieDetailsRepository.getMovieDetails(movieId)
    }
}