package com.pavelratushnyi.movies.data.movies.local

import kotlinx.coroutines.flow.Flow

internal class FakeMoviesDao(
    private val baseMoviesDao: BaseMoviesDao
) : MoviesDao {

    override fun getByIds(movieIds: LongArray): Flow<List<MovieEntity>> {
        return baseMoviesDao.getByIds(movieIds)
    }

    override suspend fun insert(vararg movies: MovieEntity) {
        return baseMoviesDao.insert(*movies)
    }
}