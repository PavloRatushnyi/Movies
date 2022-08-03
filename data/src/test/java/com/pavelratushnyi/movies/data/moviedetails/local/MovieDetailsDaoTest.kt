package com.pavelratushnyi.movies.data.moviedetails.local

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
internal class MovieDetailsDaoTest {

    private val baseMovieDetailsDao: BaseMovieDetailsDao = mock()
    private val movieDetailsDao = FakeMovieDetailsDao(baseMovieDetailsDao)

    @Test
    fun `WHEN inserting movie details contents THEN movie details contents inserted`() = runTest {
        val comedyGenre = MovieGenreEntity(
            id = 1,
            name = "comedy"
        )
        val documentaryGenre = MovieGenreEntity(
            id = 2,
            name = "documentary"
        )

        val company1 = MovieProductionCompanyEntity(
            id = 1,
            name = "company 1"
        )
        val company2 = MovieProductionCompanyEntity(
            id = 2,
            name = "company 2"
        )

        val ukraine = MovieProductionCountryEntity(
            isoCode = "UA",
            name = "Ukraine"
        )
        val poland = MovieProductionCountryEntity(
            isoCode = "PL",
            name = "Poland"
        )

        val movieDetails = MovieDetailsEntity(
            id = 1,
            title = "title 1",
            overview = "overview 1",
            posterPath = "poster path 1"
        )
        val movieDetailsContent = MovieDetailsContent(
            movieDetails = movieDetails,
            genres = listOf(comedyGenre, documentaryGenre),
            productionCompanies = listOf(company1, company2),
            productionCountries = listOf(ukraine, poland)
        )
        movieDetailsDao.insert(movieDetailsContent)

        verify(baseMovieDetailsDao).deleteMovieGenreCrossRefs(movieDetails.id)
        verify(baseMovieDetailsDao).deleteMovieProductionCompanyCrossRefs(
            movieDetails.id,
        )
        verify(baseMovieDetailsDao).deleteMovieProductionCountryCrossRefs(
            movieDetails.id,
        )
        verify(baseMovieDetailsDao).insertMovieDetails(movieDetails)
        verify(baseMovieDetailsDao).insertMovieGenres(
            listOf(
                comedyGenre,
                documentaryGenre
            )
        )
        verify(baseMovieDetailsDao).insertProductionCompanies(listOf(company1, company2))
        verify(baseMovieDetailsDao).insertProductionCountries(listOf(ukraine, poland))
        verify(baseMovieDetailsDao).insertMovieGenreCrossRefs(
            listOf(
                MovieGenreCrossRef(
                    movieId = 1,
                    genreId = 1,
                    position = 0
                ),
                MovieGenreCrossRef(
                    movieId = 1,
                    genreId = 2,
                    position = 1
                )
            )
        )
        verify(baseMovieDetailsDao).insertMovieProductionCompanyCrossRefs(
            listOf(
                MovieProductionCompanyCrossRef(
                    movieId = 1,
                    productionCompanyId = 1,
                    position = 0
                ),
                MovieProductionCompanyCrossRef(
                    movieId = 1,
                    productionCompanyId = 2,
                    position = 1
                )
            )
        )
        verify(baseMovieDetailsDao).insertMovieProductionCountryCrossRefs(
            listOf(
                MovieProductionCountryCrossRef(
                    movieId = 1,
                    countryIsoCode = "UA",
                    position = 0
                ),
                MovieProductionCountryCrossRef(
                    movieId = 1,
                    countryIsoCode = "PL",
                    position = 1
                )
            )
        )
    }

    @Test
    fun `WHEN getting movie details contents THEN movie details contents returned`() = runTest {
        val comedyGenre = MovieGenreEntity(
            id = 1,
            name = "comedy"
        )
        val company = MovieProductionCompanyEntity(
            id = 1,
            name = "company 1"
        )
        val ukraine = MovieProductionCountryEntity(
            isoCode = "UA",
            name = "Ukraine"
        )
        val movieDetails = MovieDetailsEntity(
            id = 1,
            title = "title 1",
            overview = "overview 1",
            posterPath = "poster path 1"
        )
        val movieDetailsFlow = MutableStateFlow<MovieDetailsEntity?>(null)
        whenever(baseMovieDetailsDao.getMovieDetails(1)).thenReturn(movieDetailsFlow)
        whenever(baseMovieDetailsDao.getMovieGenres(1)).thenReturn(flowOf(listOf(comedyGenre)))
        whenever(baseMovieDetailsDao.getMovieProductionCompanies(1)).thenReturn(
            flowOf(
                listOf(
                    company
                )
            )
        )
        whenever(baseMovieDetailsDao.getMovieProductionCountries(1)).thenReturn(
            flowOf(
                listOf(
                    ukraine
                )
            )
        )

        movieDetailsDao.get(1).test {
            assertEquals(null, awaitItem())
            movieDetailsFlow.emit(movieDetails)
            assertEquals(
                MovieDetailsContent(
                    movieDetails = movieDetails,
                    genres = listOf(comedyGenre),
                    productionCompanies = listOf(company),
                    productionCountries = listOf(ukraine)
                ), awaitItem()
            )
            expectNoEvents()
        }
    }
}