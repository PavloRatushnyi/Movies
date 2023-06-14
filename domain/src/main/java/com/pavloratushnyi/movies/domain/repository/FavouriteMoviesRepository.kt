package com.pavloratushnyi.movies.domain.repository

import com.pavloratushnyi.movies.domain.Resource
import com.pavloratushnyi.movies.domain.vo.Movie
import kotlinx.coroutines.flow.Flow

interface FavouriteMoviesRepository {

    fun getFavouriteMovies(): Flow<Resource<List<Movie>>>

    fun getFavouriteMoviesIds(): Flow<Resource<List<Long>>>

    suspend fun addToFavourites(id: Long)

    suspend fun removeFromFavourites(id: Long)
}