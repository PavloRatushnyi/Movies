package com.pavelratushnyi.movies.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.pavelratushnyi.movies.data.BuildConfig
import com.pavelratushnyi.movies.data.movies.MoviesRepositoryImpl
import com.pavelratushnyi.movies.data.movies.local.LocalMoviesDataSource
import com.pavelratushnyi.movies.data.movies.local.MoviesDao
import com.pavelratushnyi.movies.data.movies.local.MoviesDatabase
import com.pavelratushnyi.movies.data.movies.local.RoomLocalMoviesDataSource
import com.pavelratushnyi.movies.data.movies.remote.MoviesService
import com.pavelratushnyi.movies.data.movies.remote.RemoteMoviesDataSource
import com.pavelratushnyi.movies.data.movies.remote.TmdbRemoveMoviesDataSource
import com.pavelratushnyi.movies.data.network.TmdbApikeyInterceptor
import com.pavelratushnyi.movies.domain.repository.MoviesRepository
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    companion object {

        @Singleton
        @Provides
        fun providePreferencesDatastore(@ApplicationContext appContext: Context): DataStore<Preferences> {
            return PreferenceDataStoreFactory.create(
                produceFile = { appContext.preferencesDataStoreFile("movies") }
            )
        }

        @Singleton
        @Provides
        internal fun provideMoviesDatabase(@ApplicationContext appContext: Context): MoviesDatabase {
            return Room.databaseBuilder(
                appContext,
                MoviesDatabase::class.java,
                "movies"
            ).build()
        }

        @Provides
        internal fun provideMoviesDao(moviesDatabase: MoviesDatabase): MoviesDao {
            return moviesDatabase.moviesDao()
        }

        @Singleton
        @Provides
        fun provideOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .apply {
                            this.level = if (BuildConfig.DEBUG) {
                                HttpLoggingInterceptor.Level.BODY
                            } else {
                                HttpLoggingInterceptor.Level.NONE
                            }
                        }
                )
                .build()
        }

        @Singleton
        @Provides
        @Named("tmdb")
        fun provideTmdbOkHttpClient(okHttpClient: OkHttpClient): OkHttpClient {
            return okHttpClient.newBuilder()
                .apply { interceptors().add(0, TmdbApikeyInterceptor()) }
                .build()
        }

        @Singleton
        @Provides
        internal fun provideMoviesService(
            @Named("tmdb") okHttpClient: OkHttpClient
        ): MoviesService {
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(MoviesService.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
                .build()
                .create(MoviesService::class.java)
        }
    }

    @Binds
    internal abstract fun bindRemoteMoviesDataSource(
        tmdbRemoveMoviesDataSource: TmdbRemoveMoviesDataSource
    ): RemoteMoviesDataSource

    @Binds
    internal abstract fun bindLocalMoviesDataSource(
        roomLocalMoviesDataSource: RoomLocalMoviesDataSource
    ): LocalMoviesDataSource

    @Binds
    internal abstract fun bindMoviesRepository(
        moviesRepositoryImpl: MoviesRepositoryImpl
    ): MoviesRepository
}