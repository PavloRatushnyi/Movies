package com.pavloratushnyi.movies.domain.repository

import com.pavloratushnyi.model.MovieDetails
import com.pavloratushnyi.resource.Resource
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {

    fun getMovieDetails(id: Long): Flow<Resource<MovieDetails>>

    suspend fun refreshMovieDetails(id: Long): Result<Unit>
}