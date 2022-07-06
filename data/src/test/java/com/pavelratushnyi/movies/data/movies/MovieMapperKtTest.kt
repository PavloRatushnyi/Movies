package com.pavelratushnyi.movies.data.movies

import com.pavelratushnyi.movies.data.movies.remote.MovieDto
import com.pavelratushnyi.movies.domain.vo.Movie
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MovieMapperKtTest {

    @Test
    fun `WHEN mapping to domain THEN mapped movie returned`() {
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
}