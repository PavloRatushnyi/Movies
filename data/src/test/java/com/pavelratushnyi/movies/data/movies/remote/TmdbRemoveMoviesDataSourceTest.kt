package com.pavelratushnyi.movies.data.movies.remote

import com.pavelratushnyi.movies.data.movies.toDomain
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub

internal class TmdbRemoveMoviesDataSourceTest {

    private val service: MoviesService = mock()

    private val dataSource = TmdbRemoveMoviesDataSource(
        service = service
    )

    @Test
    fun `WHEN fetching popular movies THEN movies from api returned`() {
        val moviesDto = listOf(
            MovieDto(
                id = 1,
                title = "movie title",
                overview = "movie overview",
                posterPath = "movie poster path"
            )
        )
        service.stub {
            onBlocking { getPopularMovies() } doReturn MoviesPage(moviesDto)
        }

        runBlocking {
            assertEquals(moviesDto.map { it.toDomain() }, dataSource.getPopularMovies())
        }
    }
}