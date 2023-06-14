package com.pavloratushnyi.movies.domain.usecase

import app.cash.turbine.test
import com.pavloratushnyi.model.Movie
import com.pavloratushnyi.model.UserMovie
import com.pavloratushnyi.resource.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
internal class GetFavouriteUserMoviesStreamUseCaseTest {

    private val getFavouriteMoviesStreamUseCase: GetFavouriteMoviesStreamUseCase = mock()

    private val useCase = GetFavouriteUserMoviesStreamUseCase(getFavouriteMoviesStreamUseCase)

    @Test
    fun `WHEN getting favourite user movies THEN favourite user movies returned`() = runTest {
        val movies = listOf(
            Movie(
                id = 1,
                title = "title",
                overview = "overview",
                posterPath = "posterPath"
            )
        )
        whenever(getFavouriteMoviesStreamUseCase()).thenReturn(flowOf(Resource.Success(movies)))

        useCase().test {
            assertEquals(
                Resource.Success(movies.map { UserMovie(it, favourite = true) }),
                awaitItem()
            )
            awaitComplete()
        }
    }
}