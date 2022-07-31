package com.pavelratushnyi.movies.data.movies.local

import androidx.room.Dao
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

@Dao
internal interface MovieDetailsDao : BaseMovieDetailsDao {

    @Transaction
    suspend fun insert(vararg moviesDetailsContents: MovieDetailsContent) {
        val movieDetailsIds = mutableListOf<Long>()
        val movieDetails = mutableListOf<MovieDetailsEntity>()
        val movieGenres = linkedSetOf<MovieGenreEntity>()
        val movieProductionCompanies = linkedSetOf<MovieProductionCompanyEntity>()
        val movieProductionCountries = linkedSetOf<MovieProductionCountryEntity>()
        val movieGenreCrossRefs = mutableListOf<MovieGenreCrossRef>()
        val movieProductionCompanyCrossRefs = mutableListOf<MovieProductionCompanyCrossRef>()
        val movieProductionCountryCrossRefs = mutableListOf<MovieProductionCountryCrossRef>()
        moviesDetailsContents.forEach {
            movieDetailsIds.add(it.movieDetails.id)
            movieDetails.add(it.movieDetails)
            movieGenres.addAll(it.genres)
            movieProductionCompanies.addAll(it.productionCompanies)
            movieProductionCountries.addAll(it.productionCountries)
            movieGenreCrossRefs.addAll(
                it.genres.mapIndexed { index, genre ->
                    MovieGenreCrossRef(
                        movieId = it.movieDetails.id,
                        genreId = genre.id,
                        position = index
                    )
                }
            )
            movieProductionCompanyCrossRefs.addAll(
                it.productionCompanies.mapIndexed { index, productionCompany ->
                    MovieProductionCompanyCrossRef(
                        movieId = it.movieDetails.id,
                        productionCompanyId = productionCompany.id,
                        position = index
                    )
                }
            )
            movieProductionCountryCrossRefs.addAll(
                it.productionCountries.mapIndexed { index, productionCountry ->
                    MovieProductionCountryCrossRef(
                        movieId = it.movieDetails.id,
                        countryIsoCode = productionCountry.isoCode,
                        position = index
                    )
                }
            )
        }
        deleteMovieGenreCrossRefs(*movieDetailsIds.toLongArray())
        deleteMovieProductionCompanyCrossRefs(*movieDetailsIds.toLongArray())
        deleteMovieProductionCountryCrossRefs(*movieDetailsIds.toLongArray())

        insert(*movieDetails.toTypedArray())
        insert(*movieGenres.toTypedArray())
        insert(*movieProductionCompanies.toTypedArray())
        insert(*movieProductionCountries.toTypedArray())

        insert(*movieGenreCrossRefs.toTypedArray())
        insert(*movieProductionCompanyCrossRefs.toTypedArray())
        insert(*movieProductionCountryCrossRefs.toTypedArray())
    }

    fun get(id: Long): Flow<MovieDetailsContent?> {
        return combine(
            getMovieDetails(id),
            getMovieGenres(id),
            getMovieProductionCompanies(id),
            getMovieProductionCountries(id)
        ) { details, genres, productionCompanies, productionCountries ->
            details?.let {
                MovieDetailsContent(
                    movieDetails = details,
                    genres = genres,
                    productionCompanies = productionCompanies,
                    productionCountries = productionCountries
                )
            }
        }
    }
}