package com.pavloratushnyi.movies.ui.screen.moviedetails

object MovieDetailsDestination {

    const val movieIdArg = "movieId"
    const val route = "details/{$movieIdArg}"

    fun createNavigationRoute(movieIdArg: Long): String {
        return "details/$movieIdArg"
    }
}