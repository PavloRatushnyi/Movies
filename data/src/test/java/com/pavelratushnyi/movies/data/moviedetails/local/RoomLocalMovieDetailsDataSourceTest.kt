package com.pavelratushnyi.movies.data.moviedetails.local

import app.cash.turbine.test
import com.pavelratushnyi.movies.data.popularmovies.toEntity
import com.pavelratushnyi.movies.domain.vo.MovieDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
internal class RoomLocalMovieDetailsDataSourceTest {

    private val movieDetailsDao: MovieDetailsDao = mock()

    private val dataSource = RoomLocalMovieDetailsDataSource(movieDetailsDao)

    @Test
    fun `WHEN getting movie details THEN movie details returned`() = runTest {
        val movieDetails = MovieDetails(
            id = 1,
            title = "movie title",
            overview = "movie overview",
            posterPath = "movie poster path",
            genres = emptyList(),
            productionCompanies = emptyList(),
            productionCountries = emptyList()
        )
        whenever(movieDetailsDao.get(1)).thenReturn(flowOf(null, movieDetails.toEntity()))

        dataSource.getMovieDetails(1).test {
            assertEquals(null, awaitItem())
            assertEquals(movieDetails, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `WHEN saving movie details THEN movie details saved`() = runTest {
        val movieDetails = MovieDetails(
            id = 1,
            title = "movie title",
            overview = "movie overview",
            posterPath = "movie poster path",
            genres = emptyList(),
            productionCompanies = emptyList(),
            productionCountries = emptyList()
        )
        whenever(movieDetailsDao.get(1)).thenReturn(flowOf(movieDetails.toEntity()))

        dataSource.insertMovieDetails(movieDetails)
        verify(movieDetailsDao).insert(movieDetails.toEntity())
    }
}