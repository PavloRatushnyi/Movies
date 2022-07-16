package com.pavelratushnyi.movies.domain.usecase

import app.cash.turbine.test
import com.pavelratushnyi.movies.domain.Resource
import com.pavelratushnyi.movies.domain.repository.MoviesRepository
import com.pavelratushnyi.movies.domain.vo.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
internal class GetFavouriteMoviesStreamUseCaseTest {

    private val moviesRepository: MoviesRepository = mock()

    private val useCase = GetFavouriteMoviesStreamUseCase(moviesRepository)

    @Test
    fun `WHEN getting favourite movies THEN favourite movies returned`() = runTest {
        val movies = listOf(
            Movie(
                id = 1,
                title = "title",
                overview = "overview",
                posterPath = "posterPath"
            )
        )
        whenever(moviesRepository.getFavouriteMovies()).thenReturn(flowOf(Resource.Success(movies)))

        useCase().test {
            assertEquals(Resource.Success(movies), awaitItem())
            awaitComplete()
        }
    }
}