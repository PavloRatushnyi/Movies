package com.pavloratushnyi.movies.data.popularmovies

import com.pavloratushnyi.model.Movie
import com.pavloratushnyi.movies.data.popularmovies.local.LocalPopularMoviesDataSource
import com.pavloratushnyi.movies.data.popularmovies.remote.RemotePopularMoviesDataSource
import com.pavloratushnyi.movies.domain.Resource
import com.pavloratushnyi.movies.domain.repository.PopularMoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class PopularMoviesRepositoryImpl @Inject constructor(
    private val localPopularMoviesDataSource: LocalPopularMoviesDataSource,
    private val remotePopularMoviesDataSource: RemotePopularMoviesDataSource,
) : PopularMoviesRepository {

    override fun getPopularMovies(): Flow<Resource<List<Movie>>> {
        return flow {
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