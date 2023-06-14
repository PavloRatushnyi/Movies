package com.pavloratushnyi.movies.data.popularmovies.local

import com.pavloratushnyi.model.Movie
import com.pavloratushnyi.movies.data.movies.local.MovieEntity
import com.pavloratushnyi.movies.data.popularmovies.toDomain
import com.pavloratushnyi.movies.data.popularmovies.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

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