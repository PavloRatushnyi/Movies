package com.pavloratushnyi.resource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <O, N> Flow<Resource<O>>.mapData(transform: (O) -> N): Flow<Resource<N>> {
    return map { it.mapData(transform) }
}

fun <O, N> Resource<O>.mapData(transform: (O) -> N): Resource<N> {
    return when (this) {
        is Resource.Error -> Resource.Error(
            error = error,
            data = data?.let { transform(data) }
        )
        is Resource.Loading -> Resource.Loading(
            data = data?.let { transform(data) }
        )
        is Resource.Success -> Resource.Success(
            data = transform(data)
        )
    }
}

fun <L, R, O> Resource<L>.mergeWith(resource: Resource<R>, transform: (L, R) -> O): Resource<O> {
    fun transformIfData(): O? {
        val firstData = this.data()
        val secondData = resource.data()
        return if (firstData != null && secondData != null) {
            transform(firstData, secondData)
        } else {
            null
        }
    }
    return if (this is Resource.Success && resource is Resource.Success) {
        Resource.Success(transform(this.data, resource.data)!!)
    } else if (this is Resource.Error && resource is Resource.Error) {
        Resource.Error(
            CompositeThrowable(this.error, resource.error),
            transformIfData()
        )
    } else if (this is Resource.Error) {
        Resource.Error(
            this.error,
            transformIfData()
        )
    } else if (resource is Resource.Error) {
        Resource.Error(resource.error, transformIfData())
    } else if (this is Resource.Loading || resource is Resource.Loading) {
        Resource.Loading(transformIfData())
    } else error("error when merging resources: $this and $resource")
}
