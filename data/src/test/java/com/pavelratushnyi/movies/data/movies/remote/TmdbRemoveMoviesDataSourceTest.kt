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
        whenever(service.getPopularMovies()).thenReturn(MoviesPageDto(moviesDto))

        assertEquals(
            moviesDto.map {
                it.toDomain().copy(
                    posterPath = "https://image.tmdb.org/t/p/w500/movie poster path"
                )
            },
            dataSource.getPopularMovies()
        )
    }

    @Test
    fun `WHEN fetching movie details THEN movie details from api returned`() = runTest {
        val movieDetailsDto = MovieDetailsDto(
            id = 1,
            title = "movie title",
            overview = "movie overview",
            posterPath = "movie poster path",
            genres = listOf(MovieGenreDto(id = 1, name = "comedy")),
            productionCompanies = listOf(MovieProductionCompanyDto(id = 1, name = "worner")),
            productionCountries = listOf(
                MovieProductionCountryDto(
                    name = "Great Britain",
                    isoCode = "GB"
                )
            )
        )
        whenever(service.getMovieDetails(1)).thenReturn(movieDetailsDto)

        assertEquals(
            movieDetailsDto.toDomain().copy(
                posterPath = "https://image.tmdb.org/t/p/w780/movie poster path"
            ),
            dataSource.getMovieDetails(1)
        )
    }
}