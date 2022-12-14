package com.pavelratushnyi.movies.data.tmdb

import com.pavelratushnyi.movies.data.moviedetails.remote.MovieDetailsDto
import com.pavelratushnyi.movies.data.popularmovies.remote.MoviesPageDto
import retrofit2.http.GET
import retrofit2.http.Path

internal interface TmdbMoviesService {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }

    @GET("movie/popular")
    suspend fun getPopularMovies(): MoviesPageDto

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(@Path("movieId") movieId: Long): MovieDetailsDto
}