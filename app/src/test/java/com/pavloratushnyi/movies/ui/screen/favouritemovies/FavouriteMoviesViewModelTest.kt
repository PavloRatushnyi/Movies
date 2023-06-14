package com.pavloratushnyi.movies.ui.screen.favouritemovies

import app.cash.turbine.test
import com.pavloratushnyi.model.Movie
import com.pavloratushnyi.model.UserMovie
import com.pavloratushnyi.movies.MainDispatchersExtension
import com.pavloratushnyi.movies.domain.Resource
import com.pavloratushnyi.movies.domain.usecase.GetFavouriteUserMoviesStreamUseCase
import com.pavloratushnyi.movies.domain.usecase.RemoveFromFavouritesUseCase
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
internal class FavouriteMoviesViewModelTest {

    private val getFavouriteUserMoviesStreamUseCase: GetFavouriteUserMoviesStreamUseCase = mock()
    private val removeFromFavouritesUseCase: RemoveFromFavouritesUseCase = mock()

    private fun createViewModel() = FavouriteMoviesViewModel(
        getFavouriteUserMoviesStreamUseCase,
        removeFromFavouritesUseCase
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
        whenever(getFavouriteUserMoviesStreamUseCase()).thenReturn(flowOf(moviesResource))

        val viewModel = createViewModel()

        viewModel.uiStateFlow.test {
            assertEquals(moviesResource, awaitItem().movies)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `WHEN removing favourite THEN use case is triggered`() = runTest {
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
        whenever(getFavouriteUserMoviesStreamUseCase()).thenReturn(flowOf(moviesResource))

        val viewModel = createViewModel()
        viewModel.onEvent(FavouriteMoviesEvent.ToggleFavourite(movie))

        verify(removeFromFavouritesUseCase).invoke(movie)
    }
}