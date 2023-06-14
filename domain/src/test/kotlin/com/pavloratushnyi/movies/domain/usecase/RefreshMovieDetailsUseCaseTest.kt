package com.pavloratushnyi.movies.domain.usecase

import com.pavloratushnyi.movies.data.moviedetails.MovieDetailsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
internal class RefreshMovieDetailsUseCaseTest {

    private val movieDetailsRepository: MovieDetailsRepository = mock()

    private val useCase = RefreshMovieDetailsUseCase(movieDetailsRepository)

    @Test
    fun `WHEN refreshing movie details THEN movie details refreshed`() = runTest {
        useCase(1)

        verify(movieDetailsRepository).refreshMovieDetails(1)
    }
}