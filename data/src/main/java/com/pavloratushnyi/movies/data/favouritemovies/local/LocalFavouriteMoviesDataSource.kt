package com.pavloratushnyi.movies.data.favouritemovies.local

import com.pavloratushnyi.movies.domain.vo.Movie
import kotlinx.coroutines.flow.Flow

internal interface LocalFavouriteMoviesDataSource {

    fun getFavouriteMovies(): Flow<List<Movie>>

    fun getFavouriteMoviesIds(): Flow<List<Long>>

    suspend fun addToFavourites(id: Long)

    suspend fun removeFromFavourites(id: Long)
}