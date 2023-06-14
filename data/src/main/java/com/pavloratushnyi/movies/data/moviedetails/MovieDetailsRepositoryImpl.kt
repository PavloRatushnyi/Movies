package com.pavloratushnyi.movies.data.moviedetails

import com.pavloratushnyi.model.MovieDetails
import com.pavloratushnyi.movies.data.moviedetails.local.LocalMovieDetailsDataSource
import com.pavloratushnyi.movies.data.moviedetails.remote.RemoteMovieDetailsDataSource
import com.pavloratushnyi.movies.domain.repository.MovieDetailsRepository
import com.pavloratushnyi.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class MovieDetailsRepositoryImpl @Inject constructor(
    private val localMovieDetailsDataSource: LocalMovieDetailsDataSource,
    private val remoteMovieDetailsDataSource: RemoteMovieDetailsDataSource
) : MovieDetailsRepository {

    override fun getMovieDetails(id: Long): Flow<Resource<MovieDetails>> {
        return flow {
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