package com.pavloratushnyi.movies.domain.usecase

import com.pavloratushnyi.movies.model.UserMovie
import com.pavloratushnyi.resource.Resource
import com.pavloratushnyi.resource.mapData
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