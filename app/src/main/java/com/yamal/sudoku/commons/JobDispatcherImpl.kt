package com.yamal.sudoku.commons

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors

class JobDispatcherImpl : JobDispatcher {
    private val mainExecutor = Handler(Looper.getMainLooper())
    private val diskExecutor = Executors.newSingleThreadExecutor()

    override fun runOnUIThread(block: () -> Unit) {
        mainExecutor.post(block)
    }

    override fun runOnDiskThread(block: () -> Unit) {
        diskExecutor.execute(block)
    }
}
