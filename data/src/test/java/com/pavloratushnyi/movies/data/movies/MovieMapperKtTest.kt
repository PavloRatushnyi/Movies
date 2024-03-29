package com.pavloratushnyi.movies.data.movies

import com.pavloratushnyi.movies.data.movies.local.MovieEntity
import com.pavloratushnyi.movies.data.movies.remote.MovieDto
import com.pavloratushnyi.movies.data.popularmovies.toDomain
import com.pavloratushnyi.movies.data.popularmovies.toEntity
import com.pavloratushnyi.movies.model.Movie
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