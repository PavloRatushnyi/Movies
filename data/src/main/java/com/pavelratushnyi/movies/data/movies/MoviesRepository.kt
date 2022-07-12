package com.pavelratushnyi.movies.data.movies

import com.pavelratushnyi.movies.data.Resource
import com.pavelratushnyi.movies.domain.vo.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getPopularMovies(): Flow<Resource<List<Movie>>>
}