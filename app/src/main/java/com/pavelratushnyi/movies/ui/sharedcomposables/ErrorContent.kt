package com.pavelratushnyi.movies.ui.sharedcomposables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pavelratushnyi.movies.R

@Composable
fun ErrorContent(
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