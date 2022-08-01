package com.pavelratushnyi.movies.domain.usecase

import com.pavelratushnyi.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
internal class RefreshMovieDetailsUseCaseTest {

    private val moviesRepository: MoviesRepository = mock()

    private val useCase = RefreshMovieDetailsUseCase(moviesRepository)

    @Test
    fun `WHEN refreshing movie details THEN movie details refreshed`() = runTest {
        useCase(1)

        verify(moviesRepository).refreshMovieDetails(1)
    }
}