package com.pavelratushnyi.movies.ui.screen.favouritemovies

import com.pavelratushnyi.movies.MainDispatchersExtension
import com.pavelratushnyi.movies.domain.Resource
import com.pavelratushnyi.movies.domain.usecase.GetFavouriteUserMoviesStreamUseCase
import com.pavelratushnyi.movies.domain.usecase.RemoveFromFavouritesUseCase
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
internal class FavouriteMoviesViewModelTest {

    private val getFavouriteUserMoviesStreamUseCase: GetFavouriteUserMoviesStreamUseCase = mock()
    private val removeFromFavouritesUseCase: RemoveFromFavouritesUseCase = mock()

    private fun createViewModel() = FavouriteMoviesViewModel(
        getFavouriteUserMoviesStreamUseCase,
        removeFromFavouritesUseCase
    )

    @Test
    fun `WHEN view model is created THEN movies are fetched and set to ui state`() {
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

        assertEquals(moviesResource, viewModel.uiState.movies)
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
        viewModel.toggleFavouriteClicked(movie)

        verify(removeFromFavouritesUseCase).invoke(movie)
    }
}