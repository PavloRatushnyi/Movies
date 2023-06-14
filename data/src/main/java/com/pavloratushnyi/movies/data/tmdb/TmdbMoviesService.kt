package com.pavloratushnyi.movies.data.tmdb

import com.pavloratushnyi.movies.data.moviedetails.remote.MovieDetailsDto
import com.pavloratushnyi.movies.data.popularmovies.remote.MoviesPageDto
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