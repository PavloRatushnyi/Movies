package com.pavelratushnyi.movies.data.moviedetails.remote

import com.pavelratushnyi.movies.domain.vo.MovieDetails

internal interface RemoteMovieDetailsDataSource {

    suspend fun getMovieDetails(movieId: Long): MovieDetails
}