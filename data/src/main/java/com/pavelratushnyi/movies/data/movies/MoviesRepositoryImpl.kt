package com.pavelratushnyi.movies.data.movies

import com.pavelratushnyi.movies.data.Resource
import com.pavelratushnyi.movies.data.movies.local.LocalMoviesDataSource
import com.pavelratushnyi.movies.data.movies.remote.RemoteMoviesDataSource
import com.pavelratushnyi.movies.domain.vo.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class MoviesRepositoryImpl @Inject constructor(
    private val localMoviesDataSource: LocalMoviesDataSource,
    private val remoteMoviesDataSource: RemoteMoviesDataSource,
) : MoviesRepository {

    override suspend fun getPopularMovies(): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading())

            val localMovies = localMoviesDataSource.getPopularMovies()
            val localMoviesResource = localMovies?.let { Resource.Loading(it) }
            localMoviesResource?.let { emit(it) }

            val remoteMoviesResource = runCatching {
                remoteMoviesDataSource.getPopularMovies()
            }
                .onSuccess { localMoviesDataSource.insertPopularMovies(it) }
                .map { Resource.Success(it) }
                .getOrElse { Resource.Error(it, localMovies) }
            emit(remoteMoviesResource)
        }
    }
}