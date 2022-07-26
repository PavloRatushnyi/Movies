package com.pavelratushnyi.movies.ui.sharedcomposables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
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
import coil.compose.AsyncImage
import com.pavelratushnyi.movies.R
import com.pavelratushnyi.movies.domain.Resource
import com.pavelratushnyi.movies.domain.vo.UserMovie

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
internal fun MoviesList(
    moviesResource: Resource<List<UserMovie>>,
    toggleFavoriteClicked: (UserMovie) -> Unit,
    emptyContentModifier: Modifier = Modifier,
    errorContentModifier: Modifier = Modifier,
) {
    when (moviesResource) {
        is Resource.Loading -> {
            val movies = moviesResource.data
            if (movies != null) {
                MoviesContent(movies, toggleFavoriteClicked, emptyContentModifier)
            } else {
                LoaderContent()
            }
        }
        is Resource.Success -> MoviesContent(
            moviesResource.data,
            toggleFavoriteClicked,
            emptyContentModifier
        )
        is Resource.Error -> {
            val movies = moviesResource.data
            if (movies != null) {
                MoviesContent(movies, toggleFavoriteClicked, emptyContentModifier)
            } else {
                ErrorContent(errorContentModifier, moviesResource.error)
            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
private fun MoviesContent(
    userMovies: List<UserMovie>,
    toggleFavoriteClicked: (UserMovie) -> Unit,
    emptyContentModifier: Modifier = Modifier,
) {
    if (userMovies.isEmpty()) {
        EmptyContent(emptyContentModifier)
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 4.dp)
        ) {
            items(userMovies, key = { it.movie.id }, itemContent = { movie ->
                MovieCard(
                    modifier = Modifier.animateItemPlacement(),
                    userMovie = movie,
                    toggleFavoriteClicked = toggleFavoriteClicked
                )
            })
        }
    }
}

@Composable
private fun LoaderContent() {
    Box(
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.empty_state_no_items),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ErrorContent(
    modifier: Modifier = Modifier,
    throwable: Throwable
) {
    Box(
        modifier = modifier.padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = throwable.message ?: stringResource(R.string.error_general),
            textAlign = TextAlign.Center
        )
    }
}

@ExperimentalAnimationApi
@Composable
private fun MovieCard(
    modifier: Modifier = Modifier,
    userMovie: UserMovie,
    toggleFavoriteClicked: (UserMovie) -> Unit
) {
    Card(
        shape = RoundedCornerShape(4.dp),
        elevation = 4.dp,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                modifier = Modifier
                    .padding(8.dp)
                    .width(60.dp),
                model = userMovie.movie.posterPath,
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = userMovie.movie.title,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = userMovie.movie.overview
                )
                Spacer(modifier = Modifier.height(8.dp))
                Divider(modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(4.dp))
                val text = stringResource(
                    id = if (userMovie.favourite) {
                        R.string.toggle_favourite_remove
                    } else {
                        R.string.toggle_favourite_add
                    }
                )
                AnimatedContent(targetState = text) { targetState ->
                    TextButton(
                        onClick = { toggleFavoriteClicked(userMovie) }
                    ) {
                        Text(text = targetState)
                    }
                }
            }
        }
    }
}