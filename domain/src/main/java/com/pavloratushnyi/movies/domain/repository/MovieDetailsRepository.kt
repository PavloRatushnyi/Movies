package com.pavloratushnyi.movies.domain.repository

import com.pavloratushnyi.movies.domain.Resource
import com.pavloratushnyi.movies.domain.vo.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {

    fun getMovieDetails(id: Long): Flow<Resource<MovieDetails>>

    suspend fun refreshMovieDetails(id: Long): Result<Unit>
}