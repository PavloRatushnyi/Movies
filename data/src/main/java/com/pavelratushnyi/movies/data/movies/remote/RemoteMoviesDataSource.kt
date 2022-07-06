package com.pavelratushnyi.movies.data.movies.remote

import com.pavelratushnyi.movies.domain.vo.Movie

internal interface RemoteMoviesDataSource {

    suspend fun getPopularMovies(): List<Movie>
}