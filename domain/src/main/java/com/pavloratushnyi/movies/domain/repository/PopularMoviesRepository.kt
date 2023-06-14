package com.pavloratushnyi.movies.domain.repository

import com.pavloratushnyi.movies.domain.Resource
import com.pavloratushnyi.movies.domain.vo.Movie
import kotlinx.coroutines.flow.Flow

interface PopularMoviesRepository {

    fun getPopularMovies(): Flow<Resource<List<Movie>>>

    suspend fun refreshPopularMovies(): Result<Unit>
}