package com.pavelratushnyi.movies.data.moviedetails.local

import com.pavelratushnyi.movies.domain.vo.MovieDetails
import kotlinx.coroutines.flow.Flow

internal interface LocalMovieDetailsDataSource {

    fun getMovieDetails(id: Long): Flow<MovieDetails?>

    suspend fun insertMovieDetails(movieDetails: MovieDetails)
}