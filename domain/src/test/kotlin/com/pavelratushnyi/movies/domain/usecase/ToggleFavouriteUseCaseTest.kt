package com.pavelratushnyi.movies.domain.usecase

import com.pavelratushnyi.movies.domain.repository.MoviesRepository
import com.pavelratushnyi.movies.domain.vo.Movie
import com.pavelratushnyi.movies.domain.vo.UserMovie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
internal class ToggleFavouriteUseCaseTest {

    private val moviesRepository: MoviesRepository = mock()

    private val useCase = ToggleFavouriteUseCase(moviesRepository)

    @Test
    fun `GIVEN favourite movie WHEN toggling movie THEN movie removed from favourites`() = runTest {
        useCase(
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

        verify(moviesRepository).removeFromFavourites(1)
    }

    @Test
    fun `GIVEN not favourite movie WHEN toggling movie THEN movie added to favourites`() = runTest {
        useCase(
            UserMovie(
                movie = Movie(
                    id = 1,
                    title = "title",
                    overview = "overview",
                    posterPath = "posterPath"
                ),
                favourite = false
            )
        )

        verify(moviesRepository).addToFavourites(1)
    }
}