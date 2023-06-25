package com.pavloratushnyi.movies.data.favouritemovies.local

import com.pavloratushnyi.movies.data.movies.local.MovieEntity
import com.pavloratushnyi.movies.data.popularmovies.toDomain
import com.pavloratushnyi.movies.data.popularmovies.toEntity
import com.pavloratushnyi.movies.model.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeLocalFavouriteMoviesDataSource : LocalFavouriteMoviesDataSource {

    private val moviesFlow = MutableStateFlow<Map<Long, MovieEntity>>(emptyMap())
    private val favouriteMoviesIdsFlow = MutableStateFlow<List<Long>>(emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFavouriteMovies(): Flow<List<Movie>> {
        return favouriteMoviesIdsFlow.flatMapLatest { favouritesMoviesIds ->
            moviesFlow.map { movies ->
                favouritesMoviesIds.mapNotNull { movies[it]?.toDomain() }
            }
        }
    }

    override fun getFavouriteMoviesIds(): Flow<List<Long>> {
        return favouriteMoviesIdsFlow
    }

    override suspend fun addToFavourites(id: Long) {
        favouriteMoviesIdsFlow.update {
            listOf(id).plus(it)
        }
    }

    override suspend fun removeFromFavourites(id: Long) {
        favouriteMoviesIdsFlow.update {
            it.minus(id)
        }
    }

    fun insertMovies(movies: List<Movie>) {
        moviesFlow.update {
            it.plus(movies.associateBy { movie -> movie.id }
                .mapValues { keyValue -> keyValue.value.toEntity() })
        }
    }

    fun reset() {
        moviesFlow.update { emptyMap() }
        favouriteMoviesIdsFlow.update { emptyList() }
    }
}