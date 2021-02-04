package com.yamal.sudoku.commons.thread

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface CoroutineDispatcherProvider {
    val ioDispatcher: CoroutineDispatcher
    val mainDispatcher: CoroutineDispatcher
    val defaultDispatcher: CoroutineDispatcher
}

class CoroutineDispatcherProviderImpl : CoroutineDispatcherProvider {
    override val ioDispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

    override val defaultDispatcher: CoroutineDispatcher
        get() = Dispatchers.Default

    override val mainDispatcher: CoroutineDispatcher
        get() = Dispatchers.Main
}
