package com.pavelratushnyi.movies.data.movies.remote

import com.pavelratushnyi.movies.data.movies.toDomain
import com.pavelratushnyi.movies.domain.vo.Movie
import javax.inject.Inject

internal class TmdbRemoveMoviesDataSource @Inject constructor(
    private val service: MoviesService
) : RemoteMoviesDataSource {

    override suspend fun getPopularMovies(): List<Movie> {
        return service.getPopularMovies().results.map { it.toDomain() }
    }
}