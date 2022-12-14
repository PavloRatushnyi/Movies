package com.pavelratushnyi.movies.ui.screen.movies

import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.pavelratushnyi.movies.R
import com.pavelratushnyi.movies.domain.vo.Movie
import com.pavelratushnyi.movies.ui.screen.favouritemovies.FavouriteMoviesScreen
import com.pavelratushnyi.movies.ui.screen.popularmovies.PopularMoviesScreen
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalLifecycleComposeApi
@ExperimentalPagerApi
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
            indicator = { tabPositions ->
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
            count = pages.size,
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