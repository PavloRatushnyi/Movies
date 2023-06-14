package com.pavloratushnyi.movies.data.popularmovies

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