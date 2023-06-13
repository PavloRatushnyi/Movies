package com.pavelratushnyi.movies.data.movies.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pavelratushnyi.movies.data.moviedetails.local.MovieDetailsDao
import com.pavelratushnyi.movies.data.moviedetails.local.MovieDetailsEntity
import com.pavelratushnyi.movies.data.moviedetails.local.MovieGenreCrossRef
import com.pavelratushnyi.movies.data.moviedetails.local.MovieGenreEntity
import com.pavelratushnyi.movies.data.moviedetails.local.MovieProductionCompanyCrossRef
import com.pavelratushnyi.movies.data.moviedetails.local.MovieProductionCompanyEntity
import com.pavelratushnyi.movies.data.moviedetails.local.MovieProductionCountryCrossRef
import com.pavelratushnyi.movies.data.moviedetails.local.MovieProductionCountryEntity

@Database(
    entities = [
        MovieEntity::class,
        MovieDetailsEntity::class,
        MovieGenreEntity::class,
        MovieProductionCompanyEntity::class,
        MovieProductionCountryEntity::class,
        MovieGenreCrossRef::class,
        MovieProductionCompanyCrossRef::class,
        MovieProductionCountryCrossRef::class,
    ],
    version = 1,
    exportSchema = false,
)
internal abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun movieDetailsDao(): MovieDetailsDao
}