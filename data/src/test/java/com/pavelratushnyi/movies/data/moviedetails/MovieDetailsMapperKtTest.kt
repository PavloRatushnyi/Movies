package com.pavelratushnyi.movies.data.moviedetails

import com.pavelratushnyi.movies.data.moviedetails.local.*
import com.pavelratushnyi.movies.data.moviedetails.remote.MovieDetailsDto
import com.pavelratushnyi.movies.data.moviedetails.remote.MovieGenreDto
import com.pavelratushnyi.movies.data.moviedetails.remote.MovieProductionCompanyDto
import com.pavelratushnyi.movies.data.moviedetails.remote.MovieProductionCountryDto
import com.pavelratushnyi.movies.data.popularmovies.toDomain
import com.pavelratushnyi.movies.data.popularmovies.toEntity
import com.pavelratushnyi.movies.domain.vo.MovieDetails
import com.pavelratushnyi.movies.domain.vo.MovieGenre
import com.pavelratushnyi.movies.domain.vo.MovieProductionCompany
import com.pavelratushnyi.movies.domain.vo.MovieProductionCountry
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MovieDetailsMapperKtTest {

    @Test
    fun `WHEN mapping dto to domain THEN mapped movie details returned`() {
        assertEquals(
            MovieDetails(
                id = 1,
                title = "movie title",
                overview = "movie overview",
                posterPath = "movie poster path",
                genres = listOf(MovieGenre(id = 1, name = "comedy")),
                productionCompanies = listOf(MovieProductionCompany(id = 1, name = "worner")),
                productionCountries = listOf(
                    MovieProductionCountry(
                        name = "Great Britain",
                        isoCode = "GB"
                    )
                )
            ),
            MovieDetailsDto(
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
            ).toDomain()
        )
    }

    @Test
    fun `WHEN mapping entity to domain THEN mapped movie details returned`() {
        assertEquals(
            MovieDetails(
                id = 1,
                title = "movie title",
                overview = "movie overview",
                posterPath = "movie poster path",
                genres = listOf(MovieGenre(id = 1, name = "comedy")),
                productionCompanies = listOf(MovieProductionCompany(id = 1, name = "worner")),
                productionCountries = listOf(
                    MovieProductionCountry(
                        name = "Great Britain",
                        isoCode = "GB"
                    )
                )
            ),
            MovieDetailsContent(
                movieDetails = MovieDetailsEntity(
                    id = 1,
                    title = "movie title",
                    overview = "movie overview",
                    posterPath = "movie poster path"
                ),
                genres = listOf(MovieGenreEntity(id = 1, name = "comedy")),
                productionCompanies = listOf(MovieProductionCompanyEntity(id = 1, name = "worner")),
                productionCountries = listOf(
                    MovieProductionCountryEntity(
                        name = "Great Britain",
                        isoCode = "GB"
                    )
                )
            ).toDomain()
        )
    }

    @Test
    fun `WHEN mapping domain to entity THEN mapped movie details returned`() {
        assertEquals(
            MovieDetailsContent(
                movieDetails = MovieDetailsEntity(
                    id = 1,
                    title = "movie title",
                    overview = "movie overview",
                    posterPath = "movie poster path"
                ),
                genres = listOf(MovieGenreEntity(id = 1, name = "comedy")),
                productionCompanies = listOf(MovieProductionCompanyEntity(id = 1, name = "worner")),
                productionCountries = listOf(
                    MovieProductionCountryEntity(
                        name = "Great Britain",
                        isoCode = "GB"
                    )
                )
            ),
            MovieDetails(
                id = 1,
                title = "movie title",
                overview = "movie overview",
                posterPath = "movie poster path",
                genres = listOf(MovieGenre(id = 1, name = "comedy")),
                productionCompanies = listOf(MovieProductionCompany(id = 1, name = "worner")),
                productionCountries = listOf(
                    MovieProductionCountry(
                        name = "Great Britain",
                        isoCode = "GB"
                    )
                )
            ).toEntity()
        )
    }
}