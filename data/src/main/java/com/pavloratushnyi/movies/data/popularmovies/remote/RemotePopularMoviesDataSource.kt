package com.pavloratushnyi.movies.data.popularmovies.remote

import com.pavloratushnyi.movies.domain.vo.Movie

internal interface RemotePopularMoviesDataSource {

    suspend fun getPopularMovies(): List<Movie>
}