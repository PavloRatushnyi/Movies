package com.pavelratushnyi.movies.data.popularmovies.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pavelratushnyi.movies.data.moviedetails.local.*

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
    version = 1
)
internal abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun movieDetailsDao(): MovieDetailsDao
}