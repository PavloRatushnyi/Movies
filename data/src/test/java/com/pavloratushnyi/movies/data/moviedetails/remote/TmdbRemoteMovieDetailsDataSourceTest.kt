package com.pavloratushnyi.movies.data.moviedetails.remote

import com.pavloratushnyi.movies.data.popularmovies.toDomain
import com.pavloratushnyi.movies.data.tmdb.TmdbImageResolver
import com.pavloratushnyi.movies.data.tmdb.TmdbMoviesService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
internal class TmdbRemoteMovieDetailsDataSourceTest {

    private val service: TmdbMoviesService = mock()

    private val dataSource = TmdbRemoteMovieDetailsDataSource(
        service = service,
        tmdbImageResolver = TmdbImageResolver()
    )

    @Test
    fun `WHEN fetching movie details THEN movie details from api returned`() = runTest {
        val movieDetailsDto = MovieDetailsDto(
            id = 1,
            title = "movie title",
            overview = "movie overview",
            posterPath = "poster",
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
                posterPath = "https://image.tmdb.org/t/p/w780/poster"
            ),
            dataSource.getMovieDetails(1)
        )
    }
}