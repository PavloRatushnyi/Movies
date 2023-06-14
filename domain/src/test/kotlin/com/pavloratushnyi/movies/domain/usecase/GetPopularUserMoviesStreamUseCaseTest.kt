package com.pavloratushnyi.movies.domain.usecase

import app.cash.turbine.test
import com.pavloratushnyi.model.Movie
import com.pavloratushnyi.model.UserMovie
import com.pavloratushnyi.movies.data.favouritemovies.FavouriteMoviesRepository
import com.pavloratushnyi.movies.data.popularmovies.PopularMoviesRepository
import com.pavloratushnyi.resource.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
internal class GetPopularUserMoviesStreamUseCaseTest {

    private val popularMoviesRepository: PopularMoviesRepository = mock()
    private val favouriteMoviesRepository: FavouriteMoviesRepository = mock()

    private val useCase = GetPopularUserMoviesStreamUseCase(
        favouriteMoviesRepository,
        popularMoviesRepository
    )

    @Test
    fun `WHEN getting popular user movies THEN popular movies merged with favourites returned`() =
        runTest {
            val movies = listOf(
                Movie(
                    id = 1,
                    title = "title 1",
                    overview = "overview 1",
                    posterPath = "posterPath 1"
                ),
                Movie(
                    id = 2,
                    title = "title 2",
                    overview = "overview 2",
                    posterPath = "posterPath 2"
                )
            )
            whenever(popularMoviesRepository.getPopularMovies()).thenReturn(flowOf(Resource.Success(movies)))
            whenever(favouriteMoviesRepository.getFavouriteMoviesIds()).thenReturn(
                flowOf(
                    Resource.Success(
                        listOf(2)
                    )
                )
            )

            useCase().test {
                assertEquals(
                    Resource.Success(
                        listOf(
                            UserMovie(
                                movie = Movie(
                                    id = 1,
                                    title = "title 1",
                                    overview = "overview 1",
                                    posterPath = "posterPath 1"
                                ),
                                favourite = false
                            ),
                            UserMovie(
                                movie = Movie(
                                    id = 2,
                                    title = "title 2",
                                    overview = "overview 2",
                                    posterPath = "posterPath 2"
                                ),
                                favourite = true
                            )
                        )
                    ), awaitItem()
                )
                awaitComplete()
            }
        }
}