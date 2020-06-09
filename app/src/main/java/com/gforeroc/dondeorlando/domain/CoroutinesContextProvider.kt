package com.gforeroc.dondeorlando.domain

import kotlin.coroutines.CoroutineContext

data class CoroutinesContextProvider(
    val mainContext: CoroutineContext,
    val backgroundContext: CoroutineContext
)
