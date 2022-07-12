package com.pavelratushnyi.movies.ui.screen.movies

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.pavelratushnyi.movies.R
import com.pavelratushnyi.movies.data.Resource
import com.pavelratushnyi.movies.domain.vo.Movie

@Composable
fun MoviesScreen(viewModel: MoviesViewModel = viewModel()) {
    MoviesScreenContent(viewModel.uiState)
}

@Composable
private fun MoviesScreenContent(uiState: MoviesUiState) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        when (val moviesResource = uiState.movies) {
            is Resource.Loading -> {
                val movies = moviesResource.data
                if (movies != null) {
                    MoviesContent(movies)
                } else {
                    LoaderContent()
                }
            }
            is Resource.Success -> MoviesContent(moviesResource.data)
            is Resource.Error -> {
                val movies = moviesResource.data
                if (movies != null) {
                    MoviesContent(movies)
                } else {
                    ErrorContent(moviesResource.error)
                }
            }
        }
    }
}

@Composable
private fun MoviesContent(movies: List<Movie>) {
    LazyColumn(
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        items(movies) { movie ->
            MovieCard(movie = movie)
        }
    }
}

@Composable
private fun LoaderContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorContent(throwable: Throwable) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = throwable.message ?: stringResource(R.string.error_general),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun MovieCard(
    movie: Movie
) {
    Card(
        shape = RoundedCornerShape(4.dp),
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                modifier = Modifier
                    .padding(8.dp)
                    .width(60.dp),
                model = movie.posterPath,
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = movie.title,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = movie.overview
                )
            }
        }
    }
}