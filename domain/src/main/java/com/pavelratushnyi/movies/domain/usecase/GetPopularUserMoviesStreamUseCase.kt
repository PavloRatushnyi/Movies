package com.pavelratushnyi.movies.domain.usecase

import com.pavelratushnyi.movies.domain.Resource
import com.pavelratushnyi.movies.domain.mergeWith
import com.pavelratushnyi.movies.domain.repository.FavouriteMoviesRepository
import com.pavelratushnyi.movies.domain.repository.PopularMoviesRepository
import com.pavelratushnyi.movies.domain.vo.Movie
import com.pavelratushnyi.movies.domain.vo.UserMovie
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