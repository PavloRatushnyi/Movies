package com.pavloratushnyi.movies.data.popularmovies

import com.pavloratushnyi.model.Movie
import com.pavloratushnyi.resource.Resource
import kotlinx.coroutines.flow.Flow

interface PopularMoviesRepository {

    fun getPopularMovies(): Flow<Resource<List<Movie>>>

    suspend fun refreshPopularMovies(): Result<Unit>
}