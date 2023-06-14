package com.pavloratushnyi.movies.domain.usecase

import com.pavloratushnyi.movies.data.popularmovies.PopularMoviesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
internal class RefreshPopularMoviesUseCaseTest {

    private val popularMoviesRepository: PopularMoviesRepository = mock()

    private val useCase = RefreshPopularMoviesUseCase(popularMoviesRepository)

    @Test
    fun `WHEN refreshing popular movies THEN movies refreshed`() = runTest {
        useCase()

        verify(popularMoviesRepository).refreshPopularMovies()
    }
}