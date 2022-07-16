package com.pavelratushnyi.movies.domain.usecase

import com.pavelratushnyi.movies.domain.Resource
import com.pavelratushnyi.movies.domain.mapData
import com.pavelratushnyi.movies.domain.vo.UserMovie
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