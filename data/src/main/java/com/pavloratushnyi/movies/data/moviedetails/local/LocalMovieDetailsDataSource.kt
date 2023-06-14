package com.pavloratushnyi.movies.data.moviedetails.local

import com.pavloratushnyi.model.MovieDetails
import kotlinx.coroutines.flow.Flow

internal interface LocalMovieDetailsDataSource {

    fun getMovieDetails(id: Long): Flow<MovieDetails?>

    suspend fun insertMovieDetails(movieDetails: MovieDetails)
}