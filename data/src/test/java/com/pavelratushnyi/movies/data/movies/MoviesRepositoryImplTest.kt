package com.pavelratushnyi.movies.data.movies

import app.cash.turbine.test
import com.pavelratushnyi.movies.data.movies.remote.RemoteMoviesDataSource
import com.pavelratushnyi.movies.domain.vo.Movie
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub

internal class MoviesRepositoryImplTest {

    private val remoteDataSource: RemoteMoviesDataSource = mock()

    private val repository = MoviesRepositoryImpl(remoteDataSource)

    @Test
    fun `WHEN requesting popular movies THEN movies from remove data source returned`() {
        val movies = listOf(
            Movie(
                id = 1,
                title = "movie title",
                overview = "movie overview",
                posterPath = "movie poster path"
            )
        )
        remoteDataSource.stub {
            onBlocking { getPopularMovies() } doReturn movies
        }

        runBlocking {
            repository.getPopularMovies().test {
                assertEquals(movies, awaitItem())
                awaitComplete()
            }
        }
    }
}