package com.pavelratushnyi.movies.data.movies

import com.pavelratushnyi.movies.data.movies.local.LocalMoviesDataSource
import com.pavelratushnyi.movies.data.movies.remote.RemoteMoviesDataSource
import com.pavelratushnyi.movies.domain.Resource
import com.pavelratushnyi.movies.domain.repository.MoviesRepository
import com.pavelratushnyi.movies.domain.vo.Movie
import com.pavelratushnyi.movies.domain.vo.MovieDetails
import kotlinx.coroutines.flow.*
import javax.inject.Inject

internal class MoviesRepositoryImpl @Inject constructor(
    private val localMoviesDataSource: LocalMoviesDataSource,
    private val remoteMoviesDataSource: RemoteMoviesDataSource,
) : MoviesRepository {

    override fun getPopularMovies(): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading())

            val localMovies = localMoviesDataSource.getPopularMovies().first()
            val localMoviesResource = localMovies?.let { Resource.Loading(it) }
            localMoviesResource?.let { emit(it) }

            val remoteMoviesResource = runCatching {
                remoteMoviesDataSource.getPopularMovies()
            }
                .onSuccess { localMoviesDataSource.insertPopularMovies(it) }
                .map { Resource.Success(it) }
                .getOrElse { Resource.Error(it, localMovies) }
            emit(remoteMoviesResource)

            emitAll(
                localMoviesDataSource.getPopularMovies()
                    .filterNotNull()
                    .map { Resource.Success(it) }
            )
        }.distinctUntilChanged()
    }

    override suspend fun refreshPopularMovies(): Result<Unit> {
        return runCatching {
            remoteMoviesDataSource.getPopularMovies()
        }.onSuccess { localMoviesDataSource.insertPopularMovies(it) }.map { }
    }

    override fun getFavouriteMovies(): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading())
            emitAll(localMoviesDataSource.getFavouriteMovies().map { Resource.Success(it) })
        }.distinctUntilChanged()
    }

    override fun getFavouriteMoviesIds(): Flow<Resource<List<Long>>> {
        return flow {
            emit(Resource.Loading())
            emitAll(localMoviesDataSource.getFavouriteMoviesIds().map { Resource.Success(it) })
        }.distinctUntilChanged()
    }

    override suspend fun addToFavourites(id: Long) {
        localMoviesDataSource.addToFavourites(id)
    }

    override suspend fun removeFromFavourites(id: Long) {
        localMoviesDataSource.removeFromFavourites(id)
    }

    override fun getMovieDetails(id: Long): Flow<Resource<MovieDetails>> {
        return flow {
            emit(Resource.Loading())

            val localMovieDetails = localMoviesDataSource.getMovieDetails(id).first()
            val localMovieDetailsResource = localMovieDetails?.let { Resource.Loading(it) }
            localMovieDetailsResource?.let { emit(it) }

            val remoteMovieDetailsResource = runCatching {
                remoteMoviesDataSource.getMovieDetails(id)
            }
                .onSuccess { localMoviesDataSource.insertMovieDetails(it) }
                .map { Resource.Success(it) }
                .getOrElse { Resource.Error(it) }
            emit(remoteMovieDetailsResource)

            emitAll(
                localMoviesDataSource.getMovieDetails(id)
                    .filterNotNull()
                    .map { Resource.Success(it) }
            )
        }.distinctUntilChanged()
    }

    override suspend fun refreshMovieDetails(id: Long): Result<Unit> {
        return runCatching {
            remoteMoviesDataSource.getMovieDetails(id)
        }.onSuccess { localMoviesDataSource.insertMovieDetails(it) }.map { }
    }
}