package com.pavelratushnyi.movies.data.movies.remote

import com.pavelratushnyi.movies.domain.vo.Movie
import com.pavelratushnyi.movies.domain.vo.MovieDetails

internal interface RemoteMoviesDataSource {

    suspend fun getPopularMovies(): List<Movie>

    suspend fun getMovieDetails(movieId: Long): MovieDetails
}