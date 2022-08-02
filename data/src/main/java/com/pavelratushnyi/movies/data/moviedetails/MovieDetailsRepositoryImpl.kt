package com.pavelratushnyi.movies.data.moviedetails

import com.pavelratushnyi.movies.data.moviedetails.local.LocalMovieDetailsDataSource
import com.pavelratushnyi.movies.data.moviedetails.remote.RemoteMovieDetailsDataSource
import com.pavelratushnyi.movies.domain.Resource
import com.pavelratushnyi.movies.domain.repository.MovieDetailsRepository
import com.pavelratushnyi.movies.domain.vo.MovieDetails
import kotlinx.coroutines.flow.*
import javax.inject.Inject

internal class MovieDetailsRepositoryImpl @Inject constructor(
    private val localMovieDetailsDataSource: LocalMovieDetailsDataSource,
    private val remoteMovieDetailsDataSource: RemoteMovieDetailsDataSource
) : MovieDetailsRepository {

    override fun getMovieDetails(id: Long): Flow<Resource<MovieDetails>> {
        return flow {
            emit(Resource.Loading())

            val localMovieDetails = localMovieDetailsDataSource.getMovieDetails(id).first()
            val localMovieDetailsResource = localMovieDetails?.let { Resource.Loading(it) }
            localMovieDetailsResource?.let { emit(it) }

            val remoteMovieDetailsResource = runCatching {
                remoteMovieDetailsDataSource.getMovieDetails(id)
            }
                .onSuccess { localMovieDetailsDataSource.insertMovieDetails(it) }
                .map { Resource.Success(it) }
                .getOrElse { Resource.Error(it) }
            emit(remoteMovieDetailsResource)

            emitAll(
                localMovieDetailsDataSource.getMovieDetails(id)
                    .filterNotNull()
                    .map { Resource.Success(it) }
            )
        }.distinctUntilChanged()
    }

    override suspend fun refreshMovieDetails(id: Long): Result<Unit> {
        return runCatching {
            remoteMovieDetailsDataSource.getMovieDetails(id)
        }.onSuccess { localMovieDetailsDataSource.insertMovieDetails(it) }.map { }
    }
}