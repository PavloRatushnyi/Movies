package com.pavloratushnyi.movies.domain.usecase

import com.pavloratushnyi.model.Movie
import com.pavloratushnyi.model.UserMovie
import com.pavloratushnyi.movies.data.favouritemovies.FavouriteMoviesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
internal class RemoveFromFavouritesUseCaseTest {

    private val favouriteMoviesRepository: FavouriteMoviesRepository = mock()

    private val useCase = RemoveFromFavouritesUseCase(favouriteMoviesRepository)

    @Test
    fun `WHEN removing movie from favourites THEN movie removed from favourites`() = runTest {
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
}