package com.pavelratushnyi.movies.data.movies.local

import com.pavelratushnyi.movies.data.movies.toDomain
import com.pavelratushnyi.movies.data.movies.toEntity
import com.pavelratushnyi.movies.domain.vo.Movie
import kotlinx.coroutines.flow.*

internal class FakeLocalMoviesDataSource : LocalMoviesDataSource {

    private val popularMoviesIdsFlow = MutableStateFlow<List<Long>?>(null)
    private val moviesFlow = MutableStateFlow<Map<Long, MovieEntity>>(emptyMap())
    private val favouriteMoviesIdsFlow = MutableStateFlow<List<Long>>(emptyList())

    override fun getPopularMovies(): Flow<List<Movie>?> {
        return popularMoviesIdsFlow.flatMapLatest { moviesIds ->
            if (moviesIds == null) {
                flowOf(null)
            } else {
                moviesFlow.map { movies ->
                    moviesIds.mapNotNull { movies[it]?.toDomain() }
                }
            }
        }
    }

    override suspend fun insertPopularMovies(movies: List<Movie>) {
        popularMoviesIdsFlow.emit(movies.map { it.id })
        moviesFlow.update {
            it.plus(movies.associateBy { movie -> movie.id }
                .mapValues { keyValue -> keyValue.value.toEntity() })
        }
    }

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

    fun reset() {
        popularMoviesIdsFlow.update { null }
        moviesFlow.update { emptyMap() }
        favouriteMoviesIdsFlow.update { emptyList() }
    }
}