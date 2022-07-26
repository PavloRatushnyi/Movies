package com.pavelratushnyi.movies.domain.usecase

import com.pavelratushnyi.movies.domain.repository.MoviesRepository
import javax.inject.Inject

class RefreshPopularMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    suspend operator fun invoke(): Result<Unit> {
        return moviesRepository.refreshPopularMovies()
    }
}