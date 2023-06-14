package com.pavloratushnyi.movies.domain.usecase

import app.cash.turbine.test
import com.pavloratushnyi.model.MovieDetails
import com.pavloratushnyi.movies.data.moviedetails.MovieDetailsRepository
import com.pavloratushnyi.resource.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
internal class GetMovieDetailsStreamUseCaseTest {

    private val movieDetailsRepository: MovieDetailsRepository = mock()

    private val useCase = GetMovieDetailsStreamUseCase(movieDetailsRepository)

    @Test
    fun `WHEN getting favourite movies THEN favourite movies returned`() = runTest {
        val movieDetails = MovieDetails(
            id = 1,
            title = "movie title",
            overview = "movie overview",
            posterPath = "movie poster path",
            genres = emptyList(),
            productionCompanies = emptyList(),
            productionCountries = emptyList()
        )
        whenever(movieDetailsRepository.getMovieDetails(1)).thenReturn(flowOf(Resource.Success(movieDetails)))

        useCase(1).test {
            assertEquals(Resource.Success(movieDetails), awaitItem())
            awaitComplete()
        }
    }
}