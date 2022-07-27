package com.pavelratushnyi.movies.data.movies

import com.pavelratushnyi.movies.data.movies.remote.MovieDetailsDto
import com.pavelratushnyi.movies.data.movies.remote.MovieGenreDto
import com.pavelratushnyi.movies.data.movies.remote.MovieProductionCompanyDto
import com.pavelratushnyi.movies.data.movies.remote.MovieProductionCountryDto
import com.pavelratushnyi.movies.domain.vo.MovieDetails
import com.pavelratushnyi.movies.domain.vo.MovieGenre
import com.pavelratushnyi.movies.domain.vo.MovieProductionCompany
import com.pavelratushnyi.movies.domain.vo.MovieProductionCountry

internal fun MovieDetailsDto.toDomain() = MovieDetails(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    genres = genres.map { it.toDomain() },
    productionCompanies = productionCompanies.map { it.toDomain() },
    productionCountries = productionCountries.map { it.toDomain() }
)

internal fun MovieGenreDto.toDomain() = MovieGenre(
    id = id,
    name = name
)

internal fun MovieProductionCompanyDto.toDomain() = MovieProductionCompany(
    id = id,
    name = name
)

internal fun MovieProductionCountryDto.toDomain() = MovieProductionCountry(
    name = name,
    isoCode = isoCode
)