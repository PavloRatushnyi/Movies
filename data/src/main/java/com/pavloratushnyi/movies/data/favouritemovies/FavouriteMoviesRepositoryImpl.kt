package com.pavloratushnyi.movies.data.favouritemovies

import com.pavloratushnyi.model.Movie
import com.pavloratushnyi.movies.data.favouritemovies.local.LocalFavouriteMoviesDataSource
import com.pavloratushnyi.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class FavouriteMoviesRepositoryImpl @Inject constructor(
    private val localFavouriteMoviesDataSource: LocalFavouriteMoviesDataSource,
) : FavouriteMoviesRepository {

    override fun getFavouriteMovies(): Flow<Resource<List<Movie>>> {
        return flow {
            emitAll(localFavouriteMoviesDataSource.getFavouriteMovies().map {
                Resource.Success(it)
            })
        }.distinctUntilChanged()
    }

    override fun getFavouriteMoviesIds(): Flow<Resource<List<Long>>> {
        return flow {
            emitAll(localFavouriteMoviesDataSource.getFavouriteMoviesIds().map {
                Resource.Success(it)
            })
        }.distinctUntilChanged()
    }

    override suspend fun addToFavourites(id: Long) {
        localFavouriteMoviesDataSource.addToFavourites(id)
    }

    override suspend fun removeFromFavourites(id: Long) {
        localFavouriteMoviesDataSource.removeFromFavourites(id)
    }
}