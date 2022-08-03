package com.pavelratushnyi.movies.data.popularmovies

import com.pavelratushnyi.movies.data.popularmovies.local.MovieEntity
import com.pavelratushnyi.movies.data.popularmovies.remote.MovieDto
import com.pavelratushnyi.movies.domain.vo.Movie
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MovieMapperKtTest {

    @Test
    fun `WHEN mapping dto to domain THEN mapped movie returned`() {
        assertEquals(
            Movie(
                id = 1,
                title = "movie title",
                overview = "movie overview",
                posterPath = "movie poster path"
            ),
            MovieDto(
                id = 1,
                title = "movie title",
                overview = "movie overview",
                posterPath = "movie poster path"
            ).toDomain()
        )
    }

    @Test
    fun `WHEN mapping entity to domain THEN mapped movie returned`() {
        assertEquals(
            Movie(
                id = 1,
                title = "movie title",
                overview = "movie overview",
                posterPath = "movie poster path"
            ),
            MovieEntity(
                id = 1,
                title = "movie title",
                overview = "movie overview",
                posterPath = "movie poster path"
            ).toDomain()
        )
    }

    @Test
    fun `WHEN mapping domain to entity THEN mapped movie returned`() {
        assertEquals(
            MovieEntity(
                id = 1,
                title = "movie title",
                overview = "movie overview",
                posterPath = "movie poster path"
            ),
            Movie(
                id = 1,
                title = "movie title",
                overview = "movie overview",
                posterPath = "movie poster path"
            ).toEntity()
        )
    }
}