package com.pavelratushnyi.movies.ui.screen.movies

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.pavelratushnyi.movies.domain.vo.Movie

@Composable
fun MoviesScreen(viewModel: MoviesViewModel = viewModel()) {
    MoviesContent(viewModel.uiState)
}

@Composable
private fun MoviesContent(uiState: MoviesUiState) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        LazyColumn(
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            items(uiState.movies) { movie ->
                MovieCard(movie = movie)
            }
        }
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
            Column(modifier = Modifier
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