package com.yamal.sudoku.commons

interface JobDispatcher { // TODO replace with coroutines
    fun runOnUIThread(block: () -> Unit)
    fun runOnDiskThread(block: () -> Unit)
}
