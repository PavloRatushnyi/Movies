package com.pavelratushnyi.movies.data.movies.remote

import com.pavelratushnyi.movies.data.movies.toDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
internal class TmdbRemoveMoviesDataSourceTest {

    private val service: MoviesService = mock()

    private val dataSource = TmdbRemoveMoviesDataSource(
        service = service
    )

    @Test
    fun `WHEN fetching popular movies THEN movies from api returned`() = runTest {
        val moviesDto = listOf(
            MovieDto(
                id = 1,
                title = "movie title",
                overview = "movie overview",
                posterPath = "movie poster path"
            )
        )
        whenever(service.getPopularMovies()).thenReturn(MoviesPage(moviesDto))

        assertEquals(
            moviesDto.map {
                it.toDomain().copy(
                    posterPath = "https://image.tmdb.org/t/p/w500/movie poster path"
                )
            },
            dataSource.getPopularMovies()
        )
    }
}