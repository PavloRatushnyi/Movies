package com.pavelratushnyi.movies.ui.screen.moviedetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.pavelratushnyi.movies.R
import com.pavelratushnyi.movies.domain.Resource
import com.pavelratushnyi.movies.domain.vo.MovieDetails
import com.pavelratushnyi.movies.ui.sharedcomposables.ErrorContent
import com.pavelratushnyi.movies.ui.sharedcomposables.LoaderContent

@ExperimentalLifecycleComposeApi
@Composable
internal fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshingFlow.collectAsStateWithLifecycle()
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { viewModel.onEvent(MovieDetailsEvent.Refresh) },
    ) {
        when (val movieDetailsResource = uiState.movieDetails) {
            is Resource.Loading -> {
                val movieDetails = movieDetailsResource.data
                if (movieDetails != null) {
                    MovieDetailsContent(movieDetails)
                } else {
                    LoaderContent()
                }
            }
            is Resource.Success -> MovieDetailsContent(
                movieDetailsResource.data
            )
            is Resource.Error -> {
                val movieDetails = movieDetailsResource.data
                if (movieDetails != null) {
                    MovieDetailsContent(movieDetails)
                } else {
                    ErrorContent(
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        movieDetailsResource.error
                    )
                }
            }
        }
    }
}

@Composable
private fun MovieDetailsContent(movieDetails: MovieDetails) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    modifier = Modifier.width(120.dp),
                    model = movieDetails.posterPath,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = movieDetails.title,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = movieDetails.overview
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(
                    id = R.string.genres_template,
                    movieDetails.genres.joinToString { it.name }
                )
            )
            Text(
                text = stringResource(
                    id = R.string.production_companies_template,
                    movieDetails.productionCompanies.joinToString { it.name }
                )
            )
            Text(
                text = stringResource(
                    id = R.string.production_countries_template,
                    movieDetails.productionCountries.joinToString { it.name }
                )
            )
        }
    }
}