package com.pavelratushnyi.movies.domain

class CompositeThrowable(
    vararg exceptions: Throwable
) : Throwable(exceptions.joinToString { it.message ?: "" })