package com.pavloratushnyi.movies.data.moviedetails.local

import com.pavloratushnyi.model.MovieDetails
import com.pavloratushnyi.movies.data.popularmovies.toDomain
import com.pavloratushnyi.movies.data.popularmovies.toEntity
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