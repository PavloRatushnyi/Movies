package com.pavelratushnyi.movies.data.popularmovies.local

import com.pavelratushnyi.movies.domain.vo.Movie
import kotlinx.coroutines.flow.Flow

internal interface LocalPopularMoviesDataSource {

    fun getPopularMovies(): Flow<List<Movie>?>

    suspend fun insertPopularMovies(movies: List<Movie>)
}