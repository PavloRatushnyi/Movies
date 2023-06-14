package com.pavloratushnyi.movies.domain.usecase

import com.pavloratushnyi.movies.data.popularmovies.PopularMoviesRepository
import javax.inject.Inject

class RefreshPopularMoviesUseCase @Inject constructor(
    private val popularMoviesRepository: PopularMoviesRepository
) {

    suspend operator fun invoke(): Result<Unit> {
        return popularMoviesRepository.refreshPopularMovies()
    }
}