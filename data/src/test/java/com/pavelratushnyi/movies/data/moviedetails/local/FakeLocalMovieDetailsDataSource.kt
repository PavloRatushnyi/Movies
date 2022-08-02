package com.pavelratushnyi.movies.data.moviedetails.local

import com.pavelratushnyi.movies.data.movies.toDomain
import com.pavelratushnyi.movies.data.movies.toEntity
import com.pavelratushnyi.movies.domain.vo.MovieDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

internal class FakeLocalMovieDetailsDataSource : LocalMovieDetailsDataSource {

    private val movieDetailsFlow = MutableStateFlow<Map<Long, MovieDetailsContent>>(emptyMap())

    override fun getMovieDetails(id: Long): Flow<MovieDetails?> {
        return movieDetailsFlow.map { it[id]?.toDomain() }
    }

    override suspend fun insertMovieDetails(movieDetails: MovieDetails) {
        movieDetailsFlow.update {
            it.plus(movieDetails.id to movieDetails.toEntity())
        }
    }

    fun reset() {
        movieDetailsFlow.update { emptyMap() }
    }
}