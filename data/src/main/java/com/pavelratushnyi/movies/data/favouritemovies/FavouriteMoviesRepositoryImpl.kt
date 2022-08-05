package com.pavelratushnyi.movies.data.favouritemovies

import com.pavelratushnyi.movies.data.favouritemovies.local.LocalFavouriteMoviesDataSource
import com.pavelratushnyi.movies.domain.Resource
import com.pavelratushnyi.movies.domain.repository.FavouriteMoviesRepository
import com.pavelratushnyi.movies.domain.vo.Movie
import kotlinx.coroutines.flow.*
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