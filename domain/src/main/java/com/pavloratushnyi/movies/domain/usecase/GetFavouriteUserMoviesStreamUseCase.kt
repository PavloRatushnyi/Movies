package com.pavloratushnyi.movies.domain.usecase

import com.pavloratushnyi.model.UserMovie
import com.pavloratushnyi.movies.domain.Resource
import com.pavloratushnyi.movies.domain.mapData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavouriteUserMoviesStreamUseCase @Inject constructor(
    private val getFavouriteMoviesStreamUseCase: GetFavouriteMoviesStreamUseCase
) {

    operator fun invoke(): Flow<Resource<List<UserMovie>>> {
        return getFavouriteMoviesStreamUseCase()
            .mapData { it.map { movie -> UserMovie(movie, favourite = true) } }
    }
}