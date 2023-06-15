package com.pavloratushnyi.movies.data.popularmovies.remote

import com.pavloratushnyi.movies.data.movies.remote.MovieDto
import com.pavloratushnyi.movies.data.popularmovies.toDomain
import com.pavloratushnyi.movies.data.tmdb.TmdbImageResolver
import com.pavloratushnyi.movies.data.tmdb.TmdbMoviesService
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class TmdbRemotePopularMoviesDataSourceTest {

    private val service: TmdbMoviesService = mock()

    private val dataSource = TmdbRemotePopularMoviesDataSource(
        service = service,
        tmdbImageResolver = TmdbImageResolver()
    )

    @Test
    fun `WHEN fetching popular movies THEN movies from api returned`() = runTest {
        val moviesDto = listOf(
            MovieDto(
                id = 1,
                title = "movie title",
                overview = "movie overview",
                posterPath = "poster"
            )
        )
        whenever(service.getPopularMovies()).thenReturn(MoviesPageDto(moviesDto))

        assertEquals(
            moviesDto.map {
                it.toDomain().copy(
                    posterPath = "https://image.tmdb.org/t/p/w500/poster"
                )
            },
            dataSource.getPopularMovies()
        )
    }
}