package com.yamal.sudoku.commons

interface JobDispatcher {
    fun runOnUIThread(block: () -> Unit)
    fun runOnDiskThread(block: () -> Unit)
}
