package com.pavloratushnyi.movies.data.movies.local

import kotlinx.coroutines.flow.Flow

internal class FakeMoviesDao(
    private val baseMoviesDao: BaseMoviesDao
) : MoviesDao {

    override fun getByIds(movieIds: List<Long>): Flow<List<MovieEntity>> {
        return baseMoviesDao.getByIds(movieIds)
    }

    override suspend fun insert(movies: List<MovieEntity>) {
        return baseMoviesDao.insert(movies)
    }
}