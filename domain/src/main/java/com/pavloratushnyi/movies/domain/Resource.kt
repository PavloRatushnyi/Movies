package com.pavloratushnyi.movies.domain

sealed class Resource<T> {
    data class Loading<T>(val data: T? = null) : Resource<T>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val error: Throwable, val data: T? = null) : Resource<T>()
}

fun <T> Resource<T>.data(): T? = when (this) {
    is Resource.Loading -> data
    is Resource.Success -> data
    is Resource.Error -> data
}