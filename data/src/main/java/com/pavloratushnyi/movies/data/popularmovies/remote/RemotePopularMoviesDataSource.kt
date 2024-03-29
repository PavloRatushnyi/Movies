package com.pavloratushnyi.movies.data.popularmovies.remote

import com.pavloratushnyi.movies.model.Movie

internal interface RemotePopularMoviesDataSource {

    suspend fun getPopularMovies(): List<Movie>
}