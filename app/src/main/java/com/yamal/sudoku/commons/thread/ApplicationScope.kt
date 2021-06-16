package com.yamal.sudoku.commons.thread

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class ApplicationScope @Inject constructor(
    private val dispatcherProvider: CoroutineDispatcherProvider
) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() +  dispatcherProvider.defaultDispatcher
}