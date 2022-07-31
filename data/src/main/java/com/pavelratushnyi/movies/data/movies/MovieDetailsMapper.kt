package com.pavelratushnyi.movies.data.movies

import com.pavelratushnyi.movies.data.movies.local.*
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

internal fun MovieDetailsContent.toDomain() = MovieDetails(
    id = movieDetails.id,
    title = movieDetails.title,
    overview = movieDetails.overview,
    posterPath = movieDetails.posterPath,
    genres = genres.map { it.toDomain() },
    productionCompanies = productionCompanies.map { it.toDomain() },
    productionCountries = productionCountries.map { it.toDomain() }
)

internal fun MovieGenreEntity.toDomain() = MovieGenre(
    id = id,
    name = name
)

internal fun MovieProductionCompanyEntity.toDomain() = MovieProductionCompany(
    id = id,
    name = name
)

internal fun MovieProductionCountryEntity.toDomain() = MovieProductionCountry(
    name = name,
    isoCode = isoCode
)

internal fun MovieDetails.toEntity() = MovieDetailsContent(
    movieDetails = MovieDetailsEntity(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
    ),
    genres = genres.map { it.toEntity() },
    productionCompanies = productionCompanies.map { it.toEntity() },
    productionCountries = productionCountries.map { it.toEntity() }
)

internal fun MovieGenre.toEntity() = MovieGenreEntity(
    id = id,
    name = name
)

internal fun MovieProductionCompany.toEntity() = MovieProductionCompanyEntity(
    id = id,
    name = name
)

internal fun MovieProductionCountry.toEntity() = MovieProductionCountryEntity(
    name = name,
    isoCode = isoCode
)