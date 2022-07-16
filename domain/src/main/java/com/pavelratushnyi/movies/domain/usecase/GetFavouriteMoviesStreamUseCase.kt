package com.pavelratushnyi.movies.domain.usecase

import com.pavelratushnyi.movies.domain.Resource
import com.pavelratushnyi.movies.domain.repository.MoviesRepository
import com.pavelratushnyi.movies.domain.vo.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavouriteMoviesStreamUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    operator fun invoke(): Flow<Resource<List<Movie>>> {
        return moviesRepository.getFavouriteMovies()
    }
}