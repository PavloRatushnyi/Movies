package com.pavloratushnyi.movies.ui.screen.moviedetails

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.pavloratushnyi.movies.MainDispatchersExtension
import com.pavloratushnyi.movies.domain.Resource
import com.pavloratushnyi.movies.domain.usecase.GetMovieDetailsStreamUseCase
import com.pavloratushnyi.movies.domain.usecase.RefreshMovieDetailsUseCase
import com.pavloratushnyi.movies.domain.vo.MovieDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

@ExperimentalCoroutinesApi
@ExtendWith(MainDispatchersExtension::class)
internal class MovieDetailsViewModelTest {

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
            val movieDetails = MovieDetails(
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
        runTest {
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