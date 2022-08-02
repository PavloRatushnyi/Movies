package com.pavelratushnyi.movies.data.movies.remote

import com.pavelratushnyi.movies.data.movies.toDomain
import com.pavelratushnyi.movies.data.tmdb.TmdbImageResolver
import com.pavelratushnyi.movies.data.tmdb.TmdbMoviesService
import com.pavelratushnyi.movies.domain.vo.Movie
import javax.inject.Inject

internal class TmdbRemoteMoviesDataSource @Inject constructor(
    private val service: TmdbMoviesService,
    private val tmdbImageResolver: TmdbImageResolver
) : RemoteMoviesDataSource {

    override suspend fun getPopularMovies(): List<Movie> {
        return service.getPopularMovies().results.map {
            it.toDomain().let { movie ->
                movie.copy(
                    posterPath = movie.posterPath?.let { path ->
                        tmdbImageResolver.resolveImagePath(path, TmdbImageResolver.ImageSize.SMALL)
                    }
                )
            }
        }
    }
}