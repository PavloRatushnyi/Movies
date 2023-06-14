package com.pavloratushnyi.movies.data.favouritemovies

import com.pavloratushnyi.movies.model.Movie
import com.pavloratushnyi.resource.Resource
import kotlinx.coroutines.flow.Flow

interface FavouriteMoviesRepository {

    fun getFavouriteMovies(): Flow<Resource<List<Movie>>>

    fun getFavouriteMoviesIds(): Flow<Resource<List<Long>>>

    suspend fun addToFavourites(id: Long)

    suspend fun removeFromFavourites(id: Long)
}