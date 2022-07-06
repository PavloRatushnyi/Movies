package com.pavelratushnyi.movies.data.movies

import com.pavelratushnyi.movies.data.movies.remote.RemoteMoviesDataSource
import com.pavelratushnyi.movies.domain.vo.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

internal class MoviesRepositoryImpl @Inject constructor(
    private val remoteMoviesDataSource: RemoteMoviesDataSource
) : MoviesRepository {

    override suspend fun getPopularMovies(): Flow<List<Movie>> {
        return flowOf(remoteMoviesDataSource.getPopularMovies())
    }
}