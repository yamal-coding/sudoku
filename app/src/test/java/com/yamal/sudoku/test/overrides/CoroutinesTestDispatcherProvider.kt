package com.yamal.sudoku.test.overrides

import com.yamal.sudoku.commons.thread.CoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class CoroutinesTestDispatcherProvider(
    private val testCoroutineDispatcher: TestCoroutineDispatcher
) : CoroutineDispatcherProvider {
    override val defaultDispatcher: CoroutineDispatcher
        get() = testCoroutineDispatcher
    override val ioDispatcher: CoroutineDispatcher
        get() = testCoroutineDispatcher
    override val mainDispatcher: CoroutineDispatcher
        get() = testCoroutineDispatcher
}