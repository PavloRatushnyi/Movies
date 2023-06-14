package com.pavloratushnyi.movies.ui.screen.movies

import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.pavloratushnyi.movies.R
import com.pavloratushnyi.movies.domain.vo.Movie
import com.pavloratushnyi.movies.ui.components.pager.pagerTabIndicatorOffset
import com.pavloratushnyi.movies.ui.screen.favouritemovies.FavouriteMoviesScreen
import com.pavloratushnyi.movies.ui.screen.popularmovies.PopularMoviesScreen
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MoviesScreen(
    onMovieClicked: (Movie) -> Unit
) {
    Column {
        val pages = remember {
            arrayListOf(
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
        }

        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions: List<TabPosition> ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            }
        ) {
            pages.forEachIndexed { index, page ->
                Tab(
                    text = { Text(stringResource(page.titleResId)) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                )
            }
        }

        HorizontalPager(
            pageCount = pages.size,
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { pageIndex ->
            pages[pageIndex].content()
        }
    }
}

private class Page(
    @StringRes val titleResId: Int,
    val content: @Composable () -> Unit,
)