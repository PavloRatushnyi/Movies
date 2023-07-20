package com.pavloratushnyi.movies.feature.movies

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable

internal class Page(
    @StringRes val titleResId: Int,
    val content: @Composable () -> Unit,
)