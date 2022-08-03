package com.pavelratushnyi.movies.data.favouritemovies.local

import com.pavelratushnyi.movies.data.movies.local.MovieEntity
import com.pavelratushnyi.movies.data.popularmovies.toDomain
import com.pavelratushnyi.movies.data.popularmovies.toEntity
import com.pavelratushnyi.movies.domain.vo.Movie
import kotlinx.coroutines.flow.*

class FakeLocalFavouriteMoviesDataSource : LocalFavouriteMoviesDataSource {

    private val moviesFlow = MutableStateFlow<Map<Long, MovieEntity>>(emptyMap())
    private val favouriteMoviesIdsFlow = MutableStateFlow<List<Long>>(emptyList())

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