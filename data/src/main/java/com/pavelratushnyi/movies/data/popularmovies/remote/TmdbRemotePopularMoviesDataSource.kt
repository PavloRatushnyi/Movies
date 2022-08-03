package com.pavelratushnyi.movies.data.popularmovies.remote

import com.pavelratushnyi.movies.data.popularmovies.toDomain
import com.pavelratushnyi.movies.data.tmdb.TmdbImageResolver
import com.pavelratushnyi.movies.data.tmdb.TmdbMoviesService
import com.pavelratushnyi.movies.domain.vo.Movie
import javax.inject.Inject

internal class TmdbRemotePopularMoviesDataSource @Inject constructor(
    private val service: TmdbMoviesService,
    private val tmdbImageResolver: TmdbImageResolver
) : RemotePopularMoviesDataSource {

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