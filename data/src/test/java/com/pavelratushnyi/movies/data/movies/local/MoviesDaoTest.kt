package com.pavelratushnyi.movies.data.movies.local

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
internal class MoviesDaoTest {

    private val baseMoviesDao: BaseMoviesDao = mock()
    private val moviesDao = FakeMoviesDao(baseMoviesDao)

    @Test
    fun `WHEN getting sorted movies THEN movies sorted correctly`() = runTest {
        val firstMovie = MovieEntity(
            id = 1,
            title = "movie title 1",
            overview = "movie overview 1",
            posterPath = "movie poster path 1"
        )
        val secondMovie = MovieEntity(
            id = 1,
            title = "movie title 1",
            overview = "movie overview 1",
            posterPath = "movie poster path 1"
        )
        val thirdMovie = MovieEntity(
            id = 1,
            title = "movie title 1",
            overview = "movie overview 1",
            posterPath = "movie poster path 1"
        )
        val movies = listOf(thirdMovie, secondMovie, firstMovie)
        whenever(baseMoviesDao.getByIds(longArrayOf(1, 2, 3))).thenReturn(flowOf(movies))

        moviesDao.getAndSortByIds(longArrayOf(1, 2, 3)).test {
            assertEquals(listOf(firstMovie, secondMovie, thirdMovie), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}