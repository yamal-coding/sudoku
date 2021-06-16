package com.yamal.sudoku.commons.thread

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

interface CoroutineDispatcherProvider {
    val ioDispatcher: CoroutineDispatcher
    val mainDispatcher: CoroutineDispatcher
    val defaultDispatcher: CoroutineDispatcher
}

@Singleton
class CoroutineDispatcherProviderImpl @Inject constructor() : CoroutineDispatcherProvider {
    override val ioDispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

    override val defaultDispatcher: CoroutineDispatcher
        get() = Dispatchers.Default

    override val mainDispatcher: CoroutineDispatcher
        get() = Dispatchers.Main
}
