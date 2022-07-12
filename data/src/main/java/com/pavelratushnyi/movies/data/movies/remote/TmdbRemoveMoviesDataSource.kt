package com.pavelratushnyi.movies.data.movies.remote

import com.pavelratushnyi.movies.data.movies.toDomain
import com.pavelratushnyi.movies.domain.vo.Movie
import javax.inject.Inject

private const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

internal class TmdbRemoveMoviesDataSource @Inject constructor(
    private val service: MoviesService
) : RemoteMoviesDataSource {

    override suspend fun getPopularMovies(): List<Movie> {
        return service.getPopularMovies().results.map {
            it.toDomain().let { movie ->
                movie.copy(posterPath = if (movie.posterPath == null) null else "$TMDB_IMAGE_BASE_URL/${movie.posterPath}")
            }
        }
    }
}