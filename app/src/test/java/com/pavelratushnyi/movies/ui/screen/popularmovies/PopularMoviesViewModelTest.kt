package com.pavelratushnyi.movies.ui.screen.popularmovies

import app.cash.turbine.test
import com.pavelratushnyi.movies.MainDispatchersExtension
import com.pavelratushnyi.movies.domain.Resource
import com.pavelratushnyi.movies.domain.usecase.GetPopularUserMoviesStreamUseCase
import com.pavelratushnyi.movies.domain.usecase.ToggleFavouriteUseCase
import com.pavelratushnyi.movies.domain.vo.Movie
import com.pavelratushnyi.movies.domain.vo.UserMovie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@ExtendWith(MainDispatchersExtension::class)
internal class PopularMoviesViewModelTest {

    private val getPopularUserMoviesStreamUseCase: GetPopularUserMoviesStreamUseCase = mock()
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase = mock()

    private fun createViewModel() = PopularMoviesViewModel(
        getPopularUserMoviesStreamUseCase,
        toggleFavouriteUseCase
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
        viewModel.toggleFavouriteClicked(movie)

        verify(toggleFavouriteUseCase).invoke(movie)
    }
}