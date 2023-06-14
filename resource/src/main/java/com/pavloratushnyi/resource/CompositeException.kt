package com.pavloratushnyi.resource

class CompositeThrowable(
    vararg exceptions: Throwable
) : Throwable(exceptions.joinToString { it.message ?: "" })