package com.pavloratushnyi.movies.data.popularmovies.local

import com.pavloratushnyi.movies.model.Movie
import kotlinx.coroutines.flow.Flow

internal interface LocalPopularMoviesDataSource {

    fun getPopularMovies(): Flow<List<Movie>?>

    suspend fun insertPopularMovies(movies: List<Movie>)
}