package com.pavelratushnyi.movies.data.movies.remote

import retrofit2.http.GET
import retrofit2.http.Path

internal interface MoviesService {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }

    @GET("movie/popular")
    suspend fun getPopularMovies(): MoviesPageDto

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(@Path("movieId") movieId: Long): MovieDetailsDto
}