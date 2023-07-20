package com.pavloratushnyi.movies.feature.movies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.pavloratushnyi.movies.feature.favourite_movies.FavouriteMoviesScreen
import com.pavloratushnyi.movies.feature.popular_movies.PopularMoviesScreen
import com.pavloratushnyi.movies.model.Movie

@Composable
fun MoviesScreen(
    onMovieClicked: (Movie) -> Unit
) {
    val pages = remember { createMoviesPages(onMovieClicked) }
    HorizontalPagerWithTabs(pages)
}

private fun createMoviesPages(
    onMovieClicked: (Movie) -> Unit
): List<Page> = arrayListOf(
    Page(R.string.movies_popular) {
        PopularMoviesScreen(
            onMovieClicked = onMovieClicked
        )
    },
    Page(R.string.movies_favourites) {
        FavouriteMoviesScreen(
            onMovieClicked = onMovieClicked
        )
    },
)