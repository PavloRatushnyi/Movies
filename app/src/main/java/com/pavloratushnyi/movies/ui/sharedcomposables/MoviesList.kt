package com.pavloratushnyi.movies.ui.sharedcomposables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ShareCompat
import coil.compose.AsyncImage
import com.pavloratushnyi.movies.R
import com.pavloratushnyi.movies.domain.Resource
import com.pavloratushnyi.movies.domain.vo.Movie
import com.pavloratushnyi.movies.domain.vo.UserMovie

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
internal fun MoviesList(
    moviesResource: Resource<List<UserMovie>>,
    onToggleFavoriteClicked: (UserMovie) -> Unit,
    emptyContentModifier: Modifier = Modifier,
    errorContentModifier: Modifier = Modifier,
    onMovieClicked: (Movie) -> Unit
) {
    val lazyListState = rememberLazyListState()
    when (moviesResource) {
        is Resource.Loading -> {
            val movies = moviesResource.data
            if (movies != null) {
                MoviesContent(
                    movies,
                    onToggleFavoriteClicked,
                    emptyContentModifier,
                    onMovieClicked,
                    lazyListState
                )
            } else {
                LoaderContent()
            }
        }
        is Resource.Success -> {
            MoviesContent(
                moviesResource.data,
                onToggleFavoriteClicked,
                emptyContentModifier,
                onMovieClicked,
                lazyListState
            )
        }
        is Resource.Error -> {
            val movies = moviesResource.data
            if (movies != null) {
                MoviesContent(
                    movies,
                    onToggleFavoriteClicked,
                    emptyContentModifier,
                    onMovieClicked,
                    lazyListState
                )
            } else {
                ErrorContent(errorContentModifier, moviesResource.error)
            }
        }
    }
}

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
private fun MoviesContent(
    userMovies: List<UserMovie>,
    onToggleFavoriteClicked: (UserMovie) -> Unit,
    emptyContentModifier: Modifier = Modifier,
    onMovieClicked: (Movie) -> Unit,
    lazyListState: LazyListState
) {
    if (userMovies.isEmpty()) {
        EmptyContent(emptyContentModifier)
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 4.dp),
            state = lazyListState
        ) {
            items(userMovies, key = { it.movie.id }, itemContent = { movie ->
                MovieCard(
                    modifier = Modifier.animateItemPlacement(),
                    userMovie = movie,
                    onToggleFavoriteClicked = onToggleFavoriteClicked,
                    onMovieClicked = onMovieClicked
                )
            })
        }
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

@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
private fun MovieCard(
    modifier: Modifier = Modifier,
    userMovie: UserMovie,
    onToggleFavoriteClicked: (UserMovie) -> Unit,
    onMovieClicked: (Movie) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        onClick = { onMovieClicked(userMovie.movie) }
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

                MovieButtons(
                    userMovie = userMovie,
                    onToggleFavoriteClicked = onToggleFavoriteClicked
                )
            }
        }
    }
}

@Composable
@ExperimentalAnimationApi
private fun MovieButtons(
    userMovie: UserMovie,
    onToggleFavoriteClicked: (UserMovie) -> Unit
) {
    Row {
        val text = stringResource(
            id = if (userMovie.favourite) {
                R.string.toggle_favourite_remove
            } else {
                R.string.toggle_favourite_add
            }
        )
        AnimatedContent(targetState = text) { targetState ->
            TextButton(
                onClick = { onToggleFavoriteClicked(userMovie) }
            ) {
                Text(text = targetState)
            }
        }

        val context = LocalContext.current
        val resolvedShareIntent = remember(userMovie) {
            ShareCompat.IntentBuilder(context)
                .setType("text/plain")
                .setChooserTitle(context.getString(R.string.share))
                .setText(userMovie.movie.title)
                .createChooserIntent()
                .takeIf { it.resolveActivity(context.packageManager) != null }
        }

        if (resolvedShareIntent != null) {
            Spacer(modifier = Modifier.width(4.dp))
            TextButton(
                onClick = { context.startActivity(resolvedShareIntent) }
            ) {
                Text(text = stringResource(id = R.string.share))
            }
        }
    }
}