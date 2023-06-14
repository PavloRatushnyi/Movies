package com.pavloratushnyi.movies.data.moviedetails.local

import com.pavloratushnyi.movies.data.popularmovies.toDomain
import com.pavloratushnyi.movies.data.popularmovies.toEntity
import com.pavloratushnyi.movies.domain.vo.MovieDetails
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