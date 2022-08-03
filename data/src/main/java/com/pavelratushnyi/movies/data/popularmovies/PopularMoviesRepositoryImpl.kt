package com.pavelratushnyi.movies.data.popularmovies

import com.pavelratushnyi.movies.data.popularmovies.local.LocalPopularMoviesDataSource
import com.pavelratushnyi.movies.data.popularmovies.remote.RemotePopularMoviesDataSource
import com.pavelratushnyi.movies.domain.Resource
import com.pavelratushnyi.movies.domain.repository.PopularMoviesRepository
import com.pavelratushnyi.movies.domain.vo.Movie
import kotlinx.coroutines.flow.*
import javax.inject.Inject

internal class PopularMoviesRepositoryImpl @Inject constructor(
    private val localPopularMoviesDataSource: LocalPopularMoviesDataSource,
    private val remotePopularMoviesDataSource: RemotePopularMoviesDataSource,
) : PopularMoviesRepository {

    override fun getPopularMovies(): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading())

            val localMovies = localPopularMoviesDataSource.getPopularMovies().first()
            val localMoviesResource = localMovies?.let { Resource.Loading(it) }
            localMoviesResource?.let { emit(it) }

            val remoteMoviesResource = runCatching {
                remotePopularMoviesDataSource.getPopularMovies()
            }
                .onSuccess { localPopularMoviesDataSource.insertPopularMovies(it) }
                .map { Resource.Success(it) }
                .getOrElse { Resource.Error(it, localMovies) }
            emit(remoteMoviesResource)

            emitAll(
                localPopularMoviesDataSource.getPopularMovies()
                    .filterNotNull()
                    .map { Resource.Success(it) }
            )
        }.distinctUntilChanged()
    }

    override suspend fun refreshPopularMovies(): Result<Unit> {
        return runCatching {
            remotePopularMoviesDataSource.getPopularMovies()
        }.onSuccess { localPopularMoviesDataSource.insertPopularMovies(it) }.map { }
    }
}