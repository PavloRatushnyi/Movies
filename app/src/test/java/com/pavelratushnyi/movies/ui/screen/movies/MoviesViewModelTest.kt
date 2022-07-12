package com.pavelratushnyi.movies.ui.screen.movies

import com.pavelratushnyi.movies.MainDispatchersExtension
import com.pavelratushnyi.movies.data.movies.MoviesRepository
import com.pavelratushnyi.movies.domain.vo.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub

@ExperimentalCoroutinesApi
@ExtendWith(MainDispatchersExtension::class)
internal class MoviesViewModelTest {

    private val moviesRepository: MoviesRepository = mock()

    private fun createViewModel() = MoviesViewModel(moviesRepository)

    @Test
    fun `WHEN view model is created THEN movies are fetched and set to ui state`() = runTest {
        val movies = listOf(
            Movie(
                id = 1,
                title = "title",
                overview = "overview",
                posterPath = "posterPath"
            )
        )
        moviesRepository.stub {
            onBlocking {
                getPopularMovies()
            } doReturn flowOf(movies)
        }
        val viewModel = createViewModel()
        assertEquals(movies, viewModel.uiState.movies)
    }
}