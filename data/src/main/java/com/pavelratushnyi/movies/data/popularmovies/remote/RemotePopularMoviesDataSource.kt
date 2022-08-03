package com.pavelratushnyi.movies.data.popularmovies.remote

import com.pavelratushnyi.movies.domain.vo.Movie

internal interface RemotePopularMoviesDataSource {

    suspend fun getPopularMovies(): List<Movie>
}