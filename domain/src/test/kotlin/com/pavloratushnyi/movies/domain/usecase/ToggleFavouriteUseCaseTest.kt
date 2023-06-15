package com.pavloratushnyi.movies.domain.usecase

import com.pavloratushnyi.movies.data.favouritemovies.FavouriteMoviesRepository
import com.pavloratushnyi.movies.model.Movie
import com.pavloratushnyi.movies.model.UserMovie
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

internal class ToggleFavouriteUseCaseTest {

    private val favouriteMoviesRepository: FavouriteMoviesRepository = mock()

    private val useCase = ToggleFavouriteUseCase(favouriteMoviesRepository)

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

        verify(favouriteMoviesRepository).removeFromFavourites(1)
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

        verify(favouriteMoviesRepository).addToFavourites(1)
    }
}