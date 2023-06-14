package com.pavloratushnyi.movies.data.moviedetails

import com.pavloratushnyi.model.MovieDetails
import com.pavloratushnyi.model.MovieGenre
import com.pavloratushnyi.model.MovieProductionCompany
import com.pavloratushnyi.model.MovieProductionCountry
import com.pavloratushnyi.movies.data.moviedetails.local.MovieDetailsContent
import com.pavloratushnyi.movies.data.moviedetails.local.MovieDetailsEntity
import com.pavloratushnyi.movies.data.moviedetails.local.MovieGenreEntity
import com.pavloratushnyi.movies.data.moviedetails.local.MovieProductionCompanyEntity
import com.pavloratushnyi.movies.data.moviedetails.local.MovieProductionCountryEntity
import com.pavloratushnyi.movies.data.moviedetails.remote.MovieDetailsDto
import com.pavloratushnyi.movies.data.moviedetails.remote.MovieGenreDto
import com.pavloratushnyi.movies.data.moviedetails.remote.MovieProductionCompanyDto
import com.pavloratushnyi.movies.data.moviedetails.remote.MovieProductionCountryDto
import com.pavloratushnyi.movies.data.popularmovies.toDomain
import com.pavloratushnyi.movies.data.popularmovies.toEntity
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