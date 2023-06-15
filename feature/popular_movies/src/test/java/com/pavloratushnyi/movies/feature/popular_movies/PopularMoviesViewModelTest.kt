package com.pavloratushnyi.movies.feature.popular_movies

import app.cash.turbine.test
import com.pavloratushnyi.movies.domain.usecase.GetPopularUserMoviesStreamUseCase
import com.pavloratushnyi.movies.domain.usecase.RefreshPopularMoviesUseCase
import com.pavloratushnyi.movies.domain.usecase.ToggleFavouriteUseCase
import com.pavloratushnyi.movies.model.Movie
import com.pavloratushnyi.movies.model.UserMovie
import com.pavloratushnyi.resource.Resource
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(MainDispatchersExtension::class)
internal class PopularMoviesViewModelTest {

    private val getPopularUserMoviesStreamUseCase: GetPopularUserMoviesStreamUseCase = mock()
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase = mock()
    private val refreshPopularMoviesUseCase: RefreshPopularMoviesUseCase = mock()

    private fun createViewModel() = PopularMoviesViewModel(
        getPopularUserMoviesStreamUseCase,
        toggleFavouriteUseCase,
        refreshPopularMoviesUseCase
    )

    @Test
    fun `WHEN view model is created THEN movies are fetched and set to ui state`() = runTest {
        val movies = listOf(
            UserMovie(
                movie = Movie(
                    id = 1,
                    title = "title",
                    overview = "overview",
                    posterPath = "posterPath"
                ),
                favourite = true
            )
        )
        val moviesResource = Resource.Success(movies)
        whenever(getPopularUserMoviesStreamUseCase()).thenReturn(flowOf(moviesResource))

        val viewModel = createViewModel()

        viewModel.uiStateFlow.test {
            assertEquals(moviesResource, awaitItem().movies)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `WHEN favourite toggled THEN use case is triggered`() = runTest {
        val movie = UserMovie(
            movie = Movie(
                id = 1,
                title = "title",
                overview = "overview",
                posterPath = "posterPath"
            ),
            favourite = true
        )
        val movies = listOf(movie)
        val moviesResource = Resource.Success(movies)
        whenever(getPopularUserMoviesStreamUseCase()).thenReturn(flowOf(moviesResource))

        val viewModel = createViewModel()
        viewModel.onEvent(PopularMoviesEvent.ToggleFavourite(movie))

        verify(toggleFavouriteUseCase).invoke(movie)
    }

    @Test
    fun `WHEN refreshing movies THEN flag is changed to true AND when is refreshed THEN flag changed to false`() =
        runTest {
            whenever(refreshPopularMoviesUseCase()) doAnswer { runBlocking { Result.success(Unit) } }

            val viewModel = createViewModel()

            viewModel.isRefreshingFlow.test {
                assertEquals(false, awaitItem())
                viewModel.onEvent(PopularMoviesEvent.Refresh)
                assertEquals(true, awaitItem())
                assertEquals(false, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            verify(refreshPopularMoviesUseCase).invoke()
        }
}