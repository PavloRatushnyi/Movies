package com.pavloratushnyi.movies.feature.movie_details

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.pavloratushnyi.movies.domain.usecase.GetMovieDetailsStreamUseCase
import com.pavloratushnyi.movies.domain.usecase.RefreshMovieDetailsUseCase
import com.pavloratushnyi.movies.main_dispatcher_extension.MainDispatcherExtension
import com.pavloratushnyi.resource.Resource
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class MovieDetailsViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()

    @RegisterExtension
    private val mainDispatcherExtension = MainDispatcherExtension(dispatcher)

    private val getMovieDetailsStreamUseCase: GetMovieDetailsStreamUseCase = mock()
    private val refreshMovieDetailsUseCase: RefreshMovieDetailsUseCase = mock()
    private val savedStateHandle: SavedStateHandle = SavedStateHandle(mapOf("movieId" to 1L))

    private fun createViewModel() = MovieDetailsViewModel(
        getMovieDetailsStreamUseCase,
        refreshMovieDetailsUseCase,
        savedStateHandle
    )

    @Test
    fun `WHEN view model is created THEN movie details are fetched and set to ui state`() =
        runTest {
            val movieDetails = com.pavloratushnyi.movies.model.MovieDetails(
                id = 1,
                title = "title",
                overview = "overview",
                posterPath = "posterPath",
                genres = emptyList(),
                productionCountries = emptyList(),
                productionCompanies = emptyList()
            )
            val moviesResource = Resource.Success(movieDetails)
            whenever(getMovieDetailsStreamUseCase(1)).thenReturn(flowOf(moviesResource))

            val viewModel = createViewModel()

            viewModel.uiStateFlow.test {
                assertEquals(moviesResource, awaitItem().movieDetails)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `WHEN refreshing movie details THEN flag is changed to true AND when is refreshed THEN flag changed to false`() =
        runTest(dispatcher) {
            whenever(refreshMovieDetailsUseCase(1)) doAnswer { runBlocking { Result.success(Unit) } }

            val viewModel = createViewModel()

            viewModel.isRefreshingFlow.test {
                assertEquals(false, awaitItem())
                viewModel.onEvent(MovieDetailsEvent.Refresh)
                assertEquals(true, awaitItem())
                assertEquals(false, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            verify(refreshMovieDetailsUseCase).invoke(1)
        }
}