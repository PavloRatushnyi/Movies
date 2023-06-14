package com.pavloratushnyi.movies.domain.usecase

import com.pavloratushnyi.movies.data.favouritemovies.FavouriteMoviesRepository
import com.pavloratushnyi.movies.model.Movie
import com.pavloratushnyi.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavouriteMoviesStreamUseCase @Inject constructor(
    private val favouriteMoviesRepository: FavouriteMoviesRepository
) {

    operator fun invoke(): Flow<Resource<List<Movie>>> {
        return favouriteMoviesRepository.getFavouriteMovies()
    }
}