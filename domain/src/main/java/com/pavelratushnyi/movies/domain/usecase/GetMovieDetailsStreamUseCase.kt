package com.pavelratushnyi.movies.domain.usecase

import com.pavelratushnyi.movies.domain.Resource
import com.pavelratushnyi.movies.domain.repository.MoviesRepository
import com.pavelratushnyi.movies.domain.vo.MovieDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailsStreamUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    operator fun invoke(movieId: Long): Flow<Resource<MovieDetails>> {
        return moviesRepository.getMovieDetails(movieId)
    }
}