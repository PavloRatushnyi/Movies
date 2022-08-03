package com.pavelratushnyi.movies.data.moviedetails.local

import androidx.room.Dao
import androidx.room.Transaction
import com.pavelratushnyi.movies.data.popularmovies.local.BaseMovieDetailsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

@Dao
internal interface MovieDetailsDao : BaseMovieDetailsDao {

    @Transaction
    suspend fun insert(moviesDetailsContent: MovieDetailsContent) {
        val movieGenreCrossRefs = mutableListOf<MovieGenreCrossRef>()
        val movieProductionCompanyCrossRefs = mutableListOf<MovieProductionCompanyCrossRef>()
        val movieProductionCountryCrossRefs = mutableListOf<MovieProductionCountryCrossRef>()
        movieGenreCrossRefs.addAll(
            moviesDetailsContent.genres.mapIndexed { index, genre ->
                MovieGenreCrossRef(
                    movieId = moviesDetailsContent.movieDetails.id,
                    genreId = genre.id,
                    position = index
                )
            }
        )
        movieProductionCompanyCrossRefs.addAll(
            moviesDetailsContent.productionCompanies.mapIndexed { index, productionCompany ->
                MovieProductionCompanyCrossRef(
                    movieId = moviesDetailsContent.movieDetails.id,
                    productionCompanyId = productionCompany.id,
                    position = index
                )
            }
        )
        movieProductionCountryCrossRefs.addAll(
            moviesDetailsContent.productionCountries.mapIndexed { index, productionCountry ->
                MovieProductionCountryCrossRef(
                    movieId = moviesDetailsContent.movieDetails.id,
                    countryIsoCode = productionCountry.isoCode,
                    position = index
                )
            }
        )
        deleteMovieGenreCrossRefs(moviesDetailsContent.movieDetails.id)
        deleteMovieProductionCompanyCrossRefs(moviesDetailsContent.movieDetails.id)
        deleteMovieProductionCountryCrossRefs(moviesDetailsContent.movieDetails.id)

        insertMovieDetails(moviesDetailsContent.movieDetails)
        insertMovieGenres(moviesDetailsContent.genres.toList())
        insertProductionCompanies(moviesDetailsContent.productionCompanies.toList())
        insertProductionCountries(moviesDetailsContent.productionCountries.toList())

        insertMovieGenreCrossRefs(movieGenreCrossRefs.toList())
        insertMovieProductionCompanyCrossRefs(movieProductionCompanyCrossRefs.toList())
        insertMovieProductionCountryCrossRefs(movieProductionCountryCrossRefs.toList())
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