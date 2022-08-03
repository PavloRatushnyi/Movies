package com.pavelratushnyi.movies.domain.repository

import com.pavelratushnyi.movies.domain.Resource
import com.pavelratushnyi.movies.domain.vo.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getPopularMovies(): Flow<Resource<List<Movie>>>

    suspend fun refreshPopularMovies(): Result<Unit>
}