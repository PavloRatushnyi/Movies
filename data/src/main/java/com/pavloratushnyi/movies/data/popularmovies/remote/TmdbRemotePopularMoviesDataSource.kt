package com.pavloratushnyi.movies.data.popularmovies.remote

import com.pavloratushnyi.movies.data.popularmovies.toDomain
import com.pavloratushnyi.movies.data.tmdb.TmdbImageResolver
import com.pavloratushnyi.movies.data.tmdb.TmdbMoviesService
import com.pavloratushnyi.movies.model.Movie
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