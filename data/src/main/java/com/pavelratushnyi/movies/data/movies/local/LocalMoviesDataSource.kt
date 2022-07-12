package com.pavelratushnyi.movies.data.movies.local

import com.pavelratushnyi.movies.domain.vo.Movie

internal interface LocalMoviesDataSource {

    suspend fun getPopularMovies(): List<Movie>?

    suspend fun insertPopularMovies(movies: List<Movie>)
}