package com.pavelratushnyi.movies.data.movies.local

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
        val dramaGenre = MovieGenreEntity(
            id = 3,
            name = "drama"
        )

        val company1 = MovieProductionCompanyEntity(
            id = 1,
            name = "company 1"
        )
        val company2 = MovieProductionCompanyEntity(
            id = 2,
            name = "company 2"
        )
        val company3 = MovieProductionCompanyEntity(
            id = 3,
            name = "company 3"
        )

        val ukraine = MovieProductionCountryEntity(
            isoCode = "UA",
            name = "Ukraine"
        )
        val poland = MovieProductionCountryEntity(
            isoCode = "PL",
            name = "Poland"
        )
        val estonia = MovieProductionCountryEntity(
            isoCode = "EST",
            name = "Estonia"
        )

        val movieDetails1 = MovieDetailsEntity(
            id = 1,
            title = "title 1",
            overview = "overview 1",
            posterPath = "poster path 1"
        )
        val movieDetailsContent1 = MovieDetailsContent(
            movieDetails = movieDetails1,
            genres = listOf(comedyGenre, documentaryGenre),
            productionCompanies = listOf(company1, company2),
            productionCountries = listOf(ukraine, poland)
        )

        val movieDetails2 = MovieDetailsEntity(
            id = 2,
            title = "title 2",
            overview = "overview 2",
            posterPath = "poster path 2"
        )
        val movieDetailsContent2 = MovieDetailsContent(
            movieDetails = movieDetails2,
            genres = listOf(documentaryGenre, dramaGenre),
            productionCompanies = listOf(company2, company3),
            productionCountries = listOf(poland, estonia)
        )
        val movieDetailsContents = listOf(
            movieDetailsContent1,
            movieDetailsContent2
        )
        movieDetailsDao.insert(*movieDetailsContents.toTypedArray())

        verify(baseMovieDetailsDao).deleteMovieGenreCrossRefs(movieDetails1.id, movieDetails2.id)
        verify(baseMovieDetailsDao).deleteMovieProductionCompanyCrossRefs(
            movieDetails1.id,
            movieDetails2.id
        )
        verify(baseMovieDetailsDao).deleteMovieProductionCountryCrossRefs(
            movieDetails1.id,
            movieDetails2.id
        )
        verify(baseMovieDetailsDao).insert(movieDetails1, movieDetails2)
        verify(baseMovieDetailsDao).insert(comedyGenre, documentaryGenre, dramaGenre)
        verify(baseMovieDetailsDao).insert(company1, company2, company3)
        verify(baseMovieDetailsDao).insert(ukraine, poland, estonia)
        verify(baseMovieDetailsDao).insert(
            MovieGenreCrossRef(
                movieId = 1,
                genreId = 1,
                position = 0
            ),
            MovieGenreCrossRef(
                movieId = 1,
                genreId = 2,
                position = 1
            ),
            MovieGenreCrossRef(
                movieId = 2,
                genreId = 2,
                position = 0
            ),
            MovieGenreCrossRef(
                movieId = 2,
                genreId = 3,
                position = 1
            )
        )
        verify(baseMovieDetailsDao).insert(
            MovieProductionCompanyCrossRef(
                movieId = 1,
                productionCompanyId = 1,
                position = 0
            ),
            MovieProductionCompanyCrossRef(
                movieId = 1,
                productionCompanyId = 2,
                position = 1
            ),
            MovieProductionCompanyCrossRef(
                movieId = 2,
                productionCompanyId = 2,
                position = 0
            ),
            MovieProductionCompanyCrossRef(
                movieId = 2,
                productionCompanyId = 3,
                position = 1
            )
        )
        verify(baseMovieDetailsDao).insert(
            MovieProductionCountryCrossRef(
                movieId = 1,
                countryIsoCode = "UA",
                position = 0
            ),
            MovieProductionCountryCrossRef(
                movieId = 1,
                countryIsoCode = "PL",
                position = 1
            ),
            MovieProductionCountryCrossRef(
                movieId = 2,
                countryIsoCode = "PL",
                position = 0
            ),
            MovieProductionCountryCrossRef(
                movieId = 2,
                countryIsoCode = "EST",
                position = 1
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