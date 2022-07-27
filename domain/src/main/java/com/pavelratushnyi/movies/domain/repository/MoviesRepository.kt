package com.pavelratushnyi.movies.domain.repository

import com.pavelratushnyi.movies.domain.Resource
import com.pavelratushnyi.movies.domain.vo.Movie
import com.pavelratushnyi.movies.domain.vo.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getPopularMovies(): Flow<Resource<List<Movie>>>

    suspend fun refreshPopularMovies(): Result<Unit>

    fun getFavouriteMovies(): Flow<Resource<List<Movie>>>

    fun getFavouriteMoviesIds(): Flow<Resource<List<Long>>>

    suspend fun addToFavourites(id: Long)

    suspend fun removeFromFavourites(id: Long)

    fun getMovieDetails(id: Long): Flow<Resource<MovieDetails>>
}