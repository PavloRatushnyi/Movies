package com.pavloratushnyi.movies.data.moviedetails.remote

import com.pavloratushnyi.model.MovieDetails

internal interface RemoteMovieDetailsDataSource {

    suspend fun getMovieDetails(movieId: Long): MovieDetails
}