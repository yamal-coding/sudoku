package com.yamal.sudoku.commons.thread

interface JobDispatcher { // TODO replace with coroutines
    fun runOnUIThread(block: () -> Unit)
    fun runOnDiskThread(block: () -> Unit)
}
