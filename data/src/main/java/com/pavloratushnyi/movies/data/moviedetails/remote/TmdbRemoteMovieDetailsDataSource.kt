package com.pavloratushnyi.movies.data.moviedetails.remote

import com.pavloratushnyi.movies.data.popularmovies.toDomain
import com.pavloratushnyi.movies.data.tmdb.TmdbImageResolver
import com.pavloratushnyi.movies.data.tmdb.TmdbMoviesService
import com.pavloratushnyi.movies.model.MovieDetails
import javax.inject.Inject

internal class TmdbRemoteMovieDetailsDataSource @Inject constructor(
    private val service: TmdbMoviesService,
    private val tmdbImageResolver: TmdbImageResolver
) : RemoteMovieDetailsDataSource {

    override suspend fun getMovieDetails(movieId: Long): MovieDetails {
        return service.getMovieDetails(movieId).toDomain().let { movieDetails ->
            movieDetails.copy(
                posterPath = movieDetails.posterPath?.let {
                    tmdbImageResolver.resolveImagePath(it, TmdbImageResolver.ImageSize.BIG)
                }
            )
        }
    }
}