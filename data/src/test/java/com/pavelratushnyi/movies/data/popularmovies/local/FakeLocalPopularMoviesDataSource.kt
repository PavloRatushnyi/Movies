package com.pavelratushnyi.movies.data.popularmovies.local

import com.pavelratushnyi.movies.data.movies.local.MovieEntity
import com.pavelratushnyi.movies.data.popularmovies.toDomain
import com.pavelratushnyi.movies.data.popularmovies.toEntity
import com.pavelratushnyi.movies.domain.vo.Movie
import kotlinx.coroutines.flow.*

internal class FakeLocalPopularMoviesDataSource : LocalPopularMoviesDataSource {

    private val popularMoviesIdsFlow = MutableStateFlow<List<Long>?>(null)
    private val moviesFlow = MutableStateFlow<Map<Long, MovieEntity>>(emptyMap())

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

    fun reset() {
        popularMoviesIdsFlow.update { null }
        moviesFlow.update { emptyMap() }
    }
}