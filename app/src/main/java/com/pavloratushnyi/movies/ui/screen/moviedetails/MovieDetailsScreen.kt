package com.pavloratushnyi.movies.ui.screen.moviedetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.pavloratushnyi.movies.R
import com.pavloratushnyi.movies.model.MovieDetails
import com.pavloratushnyi.movies.shared_composables.ErrorContent
import com.pavloratushnyi.movies.shared_composables.LoaderContent
import com.pavloratushnyi.resource.Resource

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshingFlow.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.onEvent(MovieDetailsEvent.Refresh) })
    Box(Modifier.pullRefresh(pullRefreshState)) {
        MovieDetails(uiState.movieDetails)
        PullRefreshIndicator(isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
private fun MovieDetails(movieDetailsResource: Resource<MovieDetails>) {
    when (movieDetailsResource) {
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