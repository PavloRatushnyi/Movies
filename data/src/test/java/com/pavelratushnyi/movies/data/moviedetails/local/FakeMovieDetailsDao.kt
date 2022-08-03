package com.pavelratushnyi.movies.data.moviedetails.local

import com.pavelratushnyi.movies.data.popularmovies.local.BaseMovieDetailsDao
import kotlinx.coroutines.flow.Flow

internal class FakeMovieDetailsDao(
    private val baseMovieDetailsDao: BaseMovieDetailsDao
) : MovieDetailsDao {

    override suspend fun insertMovieDetails(movieDetails: MovieDetailsEntity) {
        baseMovieDetailsDao.insertMovieDetails(movieDetails)
    }

    override suspend fun insertMovieGenres(movieGenres: List<MovieGenreEntity>) {
        baseMovieDetailsDao.insertMovieGenres(movieGenres)
    }

    override suspend fun insertProductionCompanies(movieProductionCompanies: List<MovieProductionCompanyEntity>) {
        baseMovieDetailsDao.insertProductionCompanies(movieProductionCompanies)
    }

    override suspend fun insertProductionCountries(movieProductionCountries: List<MovieProductionCountryEntity>) {
        baseMovieDetailsDao.insertProductionCountries(movieProductionCountries)
    }

    override suspend fun insertMovieGenreCrossRefs(movieGenreCrossRefs: List<MovieGenreCrossRef>) {
        baseMovieDetailsDao.insertMovieGenreCrossRefs(movieGenreCrossRefs)
    }

    override suspend fun insertMovieProductionCompanyCrossRefs(movieProductionCompanyCrossRefs: List<MovieProductionCompanyCrossRef>) {
        baseMovieDetailsDao.insertMovieProductionCompanyCrossRefs(movieProductionCompanyCrossRefs)
    }

    override suspend fun insertMovieProductionCountryCrossRefs(movieProductionCountryCrossRefs: List<MovieProductionCountryCrossRef>) {
        baseMovieDetailsDao.insertMovieProductionCountryCrossRefs(movieProductionCountryCrossRefs)
    }

    override suspend fun deleteMovieGenreCrossRefs(movieId: Long) {
        baseMovieDetailsDao.deleteMovieGenreCrossRefs(movieId)
    }

    override suspend fun deleteMovieProductionCompanyCrossRefs(movieId: Long) {
        baseMovieDetailsDao.deleteMovieProductionCompanyCrossRefs(movieId)
    }

    override suspend fun deleteMovieProductionCountryCrossRefs(movieId: Long) {
        baseMovieDetailsDao.deleteMovieProductionCountryCrossRefs(movieId)
    }

    override fun getMovieDetails(id: Long): Flow<MovieDetailsEntity?> {
        return baseMovieDetailsDao.getMovieDetails(id)
    }

    override fun getMovieGenres(movieId: Long): Flow<List<MovieGenreEntity>> {
        return baseMovieDetailsDao.getMovieGenres(movieId)
    }

    override fun getMovieProductionCompanies(movieId: Long): Flow<List<MovieProductionCompanyEntity>> {
        return baseMovieDetailsDao.getMovieProductionCompanies(movieId)
    }

    override fun getMovieProductionCountries(movieId: Long): Flow<List<MovieProductionCountryEntity>> {
        return baseMovieDetailsDao.getMovieProductionCountries(movieId)
    }
}