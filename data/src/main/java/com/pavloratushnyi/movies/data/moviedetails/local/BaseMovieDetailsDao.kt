package com.pavloratushnyi.movies.data.moviedetails.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import kotlinx.coroutines.flow.Flow

internal interface BaseMovieDetailsDao {

    @Query("SELECT * FROM movie_details WHERE id = :id")
    fun getMovieDetails(id: Long): Flow<MovieDetailsEntity?>

    @Query(
        """
        SELECT * FROM movie_genres
        INNER JOIN movie_genre_cross_ref ON movie_genre_cross_ref.genre_id = movie_genres.id
        WHERE movie_genre_cross_ref.movie_id = :movieId
        ORDER BY movie_genre_cross_ref.position
        """
    )
    @RewriteQueriesToDropUnusedColumns
    fun getMovieGenres(movieId: Long): Flow<List<MovieGenreEntity>>

    @Query(
        """
        SELECT * FROM movie_production_company
        INNER JOIN movie_production_company_cross_ref ON movie_production_company_cross_ref.production_company_id = movie_production_company.id
        WHERE movie_production_company_cross_ref.movie_id = :movieId
        ORDER BY movie_production_company_cross_ref.position
        """
    )
    @RewriteQueriesToDropUnusedColumns
    fun getMovieProductionCompanies(movieId: Long): Flow<List<MovieProductionCompanyEntity>>

    @Query(
        """
        SELECT * FROM movie_production_country
        INNER JOIN movie_production_country_cross_ref ON movie_production_country_cross_ref.country_iso_code = movie_production_country.iso_code
        WHERE movie_production_country_cross_ref.movie_id = :movieId
        ORDER BY movie_production_country_cross_ref.position
        """
    )
    @RewriteQueriesToDropUnusedColumns
    fun getMovieProductionCountries(movieId: Long): Flow<List<MovieProductionCountryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetails(movieDetails: MovieDetailsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieGenres(movieGenres: List<MovieGenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductionCompanies(movieProductionCompanies: List<MovieProductionCompanyEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductionCountries(movieProductionCountries: List<MovieProductionCountryEntity>)

    @Insert
    suspend fun insertMovieGenreCrossRefs(movieGenreCrossRefs: List<MovieGenreCrossRef>)

    @Insert
    suspend fun insertMovieProductionCompanyCrossRefs(movieProductionCompanyCrossRefs: List<MovieProductionCompanyCrossRef>)

    @Insert
    suspend fun insertMovieProductionCountryCrossRefs(movieProductionCountryCrossRefs: List<MovieProductionCountryCrossRef>)

    @Query("DELETE FROM movie_genre_cross_ref WHERE movie_id = :movieId")
    suspend fun deleteMovieGenreCrossRefs(movieId: Long)

    @Query("DELETE FROM movie_production_company_cross_ref WHERE movie_id = :movieId")
    suspend fun deleteMovieProductionCompanyCrossRefs(movieId: Long)

    @Query("DELETE FROM movie_production_country_cross_ref WHERE movie_id = :movieId")
    suspend fun deleteMovieProductionCountryCrossRefs(movieId: Long)
}