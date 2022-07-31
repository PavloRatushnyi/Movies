package com.pavelratushnyi.movies.data.movies.local

import kotlinx.coroutines.flow.Flow

internal class FakeMovieDetailsDao(
    private val baseMovieDetailsDao: BaseMovieDetailsDao
) : MovieDetailsDao {

    override suspend fun insert(vararg movieDetails: MovieDetailsEntity) {
        baseMovieDetailsDao.insert(*movieDetails)
    }

    override suspend fun insert(vararg movieGenres: MovieGenreEntity) {
        baseMovieDetailsDao.insert(*movieGenres)
    }

    override suspend fun insert(vararg movieProductionCompanies: MovieProductionCompanyEntity) {
        baseMovieDetailsDao.insert(*movieProductionCompanies)
    }

    override suspend fun insert(vararg movieProductionCountries: MovieProductionCountryEntity) {
        baseMovieDetailsDao.insert(*movieProductionCountries)
    }

    override suspend fun insert(vararg movieGenreCrossRefs: MovieGenreCrossRef) {
        baseMovieDetailsDao.insert(*movieGenreCrossRefs)
    }

    override suspend fun insert(vararg movieProductionCompanyCrossRefs: MovieProductionCompanyCrossRef) {
        baseMovieDetailsDao.insert(*movieProductionCompanyCrossRefs)
    }

    override suspend fun insert(vararg movieProductionCountryCrossRefs: MovieProductionCountryCrossRef) {
        baseMovieDetailsDao.insert(*movieProductionCountryCrossRefs)
    }

    override suspend fun deleteMovieGenreCrossRefs(vararg movieIds: Long) {
        baseMovieDetailsDao.deleteMovieGenreCrossRefs(*movieIds)
    }

    override suspend fun deleteMovieProductionCompanyCrossRefs(vararg movieIds: Long) {
        baseMovieDetailsDao.deleteMovieProductionCompanyCrossRefs(*movieIds)
    }

    override suspend fun deleteMovieProductionCountryCrossRefs(vararg movieIds: Long) {
        baseMovieDetailsDao.deleteMovieProductionCountryCrossRefs(*movieIds)
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