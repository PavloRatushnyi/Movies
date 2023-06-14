package com.pavloratushnyi.movies.domain.usecase

import com.pavloratushnyi.model.Movie
import com.pavloratushnyi.movies.domain.Resource
import com.pavloratushnyi.movies.domain.repository.FavouriteMoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavouriteMoviesStreamUseCase @Inject constructor(
    private val favouriteMoviesRepository: FavouriteMoviesRepository
) {

    operator fun invoke(): Flow<Resource<List<Movie>>> {
        return favouriteMoviesRepository.getFavouriteMovies()
    }
}