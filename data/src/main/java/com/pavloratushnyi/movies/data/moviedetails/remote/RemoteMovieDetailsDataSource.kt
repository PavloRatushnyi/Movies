package com.pavloratushnyi.movies.data.moviedetails.remote

import com.pavloratushnyi.movies.domain.vo.MovieDetails

internal interface RemoteMovieDetailsDataSource {

    suspend fun getMovieDetails(movieId: Long): MovieDetails
}