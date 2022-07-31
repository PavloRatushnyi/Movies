package com.pavelratushnyi.movies.data.movies.local

import com.pavelratushnyi.movies.domain.vo.Movie
import com.pavelratushnyi.movies.domain.vo.MovieDetails
import kotlinx.coroutines.flow.Flow

internal interface LocalMoviesDataSource {

    fun getPopularMovies(): Flow<List<Movie>?>

    suspend fun insertPopularMovies(movies: List<Movie>)

    fun getFavouriteMovies(): Flow<List<Movie>>

    fun getFavouriteMoviesIds(): Flow<List<Long>>

    suspend fun addToFavourites(id: Long)

    suspend fun removeFromFavourites(id: Long)

    fun getMovieDetails(id: Long): Flow<MovieDetails?>

    suspend fun insertMovieDetails(movieDetails: MovieDetails)
}