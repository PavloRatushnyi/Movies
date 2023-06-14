package com.pavloratushnyi.movies.domain.repository

import com.pavloratushnyi.model.Movie
import com.pavloratushnyi.movies.domain.Resource
import kotlinx.coroutines.flow.Flow

interface PopularMoviesRepository {

    fun getPopularMovies(): Flow<Resource<List<Movie>>>

    suspend fun refreshPopularMovies(): Result<Unit>
}