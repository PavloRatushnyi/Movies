package com.pavloratushnyi.movies.data.tmdb

import javax.inject.Inject

internal class TmdbImageResolver @Inject constructor() {

    fun resolveImagePath(path: String, size: ImageSize): String {
        val baseImageUrl = when (size) {
            ImageSize.SMALL -> TMDB_IMAGE_500_BASE_URL
            ImageSize.BIG -> TMDB_IMAGE_780_BASE_URL
        }
        return "$baseImageUrl/$path"
    }

    enum class ImageSize {
        SMALL, BIG
    }

    companion object {
        private const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p"
        private const val TMDB_IMAGE_500_BASE_URL = "$TMDB_IMAGE_BASE_URL/w500"
        private const val TMDB_IMAGE_780_BASE_URL = "$TMDB_IMAGE_BASE_URL/w780"
    }
}