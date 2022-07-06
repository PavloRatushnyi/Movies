package com.pavelratushnyi.movies.data.movies.remote

import retrofit2.http.GET

internal interface MoviesService {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }

    @GET("movie/popular")
    suspend fun getPopularMovies(): MoviesPage
}