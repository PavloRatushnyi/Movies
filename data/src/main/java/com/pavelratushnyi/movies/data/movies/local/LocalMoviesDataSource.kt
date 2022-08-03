package com.pavelratushnyi.movies.data.movies.local

import com.pavelratushnyi.movies.domain.vo.Movie
import kotlinx.coroutines.flow.Flow

internal interface LocalMoviesDataSource {

    fun getPopularMovies(): Flow<List<Movie>?>

    suspend fun insertPopularMovies(movies: List<Movie>)
}