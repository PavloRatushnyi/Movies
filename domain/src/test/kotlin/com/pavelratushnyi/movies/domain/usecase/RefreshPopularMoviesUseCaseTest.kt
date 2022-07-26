package com.pavelratushnyi.movies.domain.usecase

import com.pavelratushnyi.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
internal class RefreshPopularMoviesUseCaseTest {

    private val moviesRepository: MoviesRepository = mock()

    private val useCase = RefreshPopularMoviesUseCase(moviesRepository)

    @Test
    fun `WHEN refreshing popular movies THEN movies refreshed`() = runTest {
        useCase()

        verify(moviesRepository).refreshPopularMovies()
    }
}