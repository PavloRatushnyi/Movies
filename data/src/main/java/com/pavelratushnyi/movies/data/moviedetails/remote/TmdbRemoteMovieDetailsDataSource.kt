package com.pavelratushnyi.movies.data.moviedetails.remote

import com.pavelratushnyi.movies.data.popularmovies.toDomain
import com.pavelratushnyi.movies.data.tmdb.TmdbImageResolver
import com.pavelratushnyi.movies.data.tmdb.TmdbMoviesService
import com.pavelratushnyi.movies.domain.vo.MovieDetails
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