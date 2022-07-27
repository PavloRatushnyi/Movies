package com.pavelratushnyi.movies.data.movies.remote

import com.pavelratushnyi.movies.data.movies.toDomain
import com.pavelratushnyi.movies.domain.vo.Movie
import com.pavelratushnyi.movies.domain.vo.MovieDetails
import javax.inject.Inject

private const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p"
private const val TMDB_IMAGE_500_BASE_URL = "$TMDB_IMAGE_BASE_URL/w500"
private const val TMDB_IMAGE_780_BASE_URL = "$TMDB_IMAGE_BASE_URL/w780"

internal class TmdbRemoveMoviesDataSource @Inject constructor(
    private val service: MoviesService
) : RemoteMoviesDataSource {

    override suspend fun getPopularMovies(): List<Movie> {
        return service.getPopularMovies().results.map {
            it.toDomain().let { movie ->
                movie.copy(
                    posterPath = movie.posterPath.applyBaseImageUrl(
                        TMDB_IMAGE_500_BASE_URL
                    )
                )
            }
        }
    }

    override suspend fun getMovieDetails(movieId: Long): MovieDetails {
        return service.getMovieDetails(movieId).toDomain().let { movieDetails ->
            movieDetails.copy(
                posterPath = movieDetails.posterPath.applyBaseImageUrl(
                    TMDB_IMAGE_780_BASE_URL
                )
            )
        }
    }

    private fun String?.applyBaseImageUrl(baseImageUrl: String): String? {
        return if (this == null) null else "$baseImageUrl/${this}"
    }
}