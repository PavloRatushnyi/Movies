package com.pavelratushnyi.movies.domain.repository

import com.pavelratushnyi.movies.domain.Resource
import com.pavelratushnyi.movies.domain.vo.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {

    fun getMovieDetails(id: Long): Flow<Resource<MovieDetails>>

    suspend fun refreshMovieDetails(id: Long): Result<Unit>
}