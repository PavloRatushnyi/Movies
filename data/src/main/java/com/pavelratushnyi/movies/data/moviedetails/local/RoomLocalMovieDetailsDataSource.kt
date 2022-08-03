package com.pavelratushnyi.movies.data.moviedetails.local

import com.pavelratushnyi.movies.data.popularmovies.toDomain
import com.pavelratushnyi.movies.data.popularmovies.toEntity
import com.pavelratushnyi.movies.domain.vo.MovieDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class RoomLocalMovieDetailsDataSource @Inject constructor(
    private val movieDetailsDao: MovieDetailsDao
) : LocalMovieDetailsDataSource {

    override fun getMovieDetails(id: Long): Flow<MovieDetails?> {
        return movieDetailsDao.get(id).map { it?.toDomain() }
    }

    override suspend fun insertMovieDetails(movieDetails: MovieDetails) {
        movieDetailsDao.insert(movieDetails.toEntity())
    }
}