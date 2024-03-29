package com.pavloratushnyi.movies.domain.usecase

import com.pavloratushnyi.movies.data.favouritemovies.FavouriteMoviesRepository
import com.pavloratushnyi.movies.data.popularmovies.PopularMoviesRepository
import com.pavloratushnyi.movies.model.Movie
import com.pavloratushnyi.movies.model.UserMovie
import com.pavloratushnyi.resource.Resource
import com.pavloratushnyi.resource.mergeWith
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetPopularUserMoviesStreamUseCase @Inject constructor(
    private val favouriteMoviesRepository: FavouriteMoviesRepository,
    private val popularMoviesRepository: PopularMoviesRepository
) {

    operator fun invoke(): Flow<Resource<List<UserMovie>>> {
        return combine(
            favouriteMoviesRepository.getFavouriteMoviesIds(),
            popularMoviesRepository.getPopularMovies()
        ) { favouriteMoviesIds, popularMovies ->
            fun createUserMovies(
                favouriteMoviesIds: List<Long>,
                popularMovies: List<Movie>
            ): List<UserMovie> {
                val favouriteMoviesIdsSet = favouriteMoviesIds.toSet()
                return popularMovies.map {
                    UserMovie(
                        it,
                        favouriteMoviesIdsSet.contains(it.id)
                    )
                }
            }

            return@combine favouriteMoviesIds.mergeWith(popularMovies) { favouriteMoviesIdsData, popularMoviesData ->
                createUserMovies(favouriteMoviesIdsData, popularMoviesData)
            }
        }
    }
}