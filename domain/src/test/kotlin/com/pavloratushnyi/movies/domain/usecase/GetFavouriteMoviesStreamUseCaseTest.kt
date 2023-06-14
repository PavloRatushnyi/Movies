package com.pavloratushnyi.movies.domain.usecase

import app.cash.turbine.test
import com.pavloratushnyi.model.Movie
import com.pavloratushnyi.movies.domain.Resource
import com.pavloratushnyi.movies.domain.repository.FavouriteMoviesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
internal class GetFavouriteMoviesStreamUseCaseTest {

    private val favouriteMoviesRepository: FavouriteMoviesRepository = mock()

    private val useCase = GetFavouriteMoviesStreamUseCase(favouriteMoviesRepository)

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
        whenever(favouriteMoviesRepository.getFavouriteMovies()).thenReturn(flowOf(Resource.Success(movies)))

        useCase().test {
            assertEquals(Resource.Success(movies), awaitItem())
            awaitComplete()
        }
    }
}