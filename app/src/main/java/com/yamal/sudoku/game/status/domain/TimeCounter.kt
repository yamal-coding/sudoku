package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.commons.thread.di.DefaultDispatcher
import com.yamal.sudoku.game.status.data.GameStatusRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@ViewModelScoped
open class TimeCounter @Inject constructor(
    @DefaultDispatcher dispatcher: CoroutineDispatcher,
    private val gameStatusRepository: GameStatusRepository,
) {
    private val _timeCounterState = MutableStateFlow<Long?>(null)
    val timeCounterState: Flow<Long?> = _timeCounterState

    private val scope = CoroutineScope(dispatcher)
    private var timerJob: Job? = null

    private val alreadyStarted = AtomicBoolean(false)

    open fun start(initialSeconds: Long) {
        onStarted {
            timerJob = scope.launch {
                updateSeconds(initialSeconds)
                startTimeCounter()
            }
        }
    }

    open fun resume() {
        onStarted {
            timerJob = scope.launch {
                startTimeCounter()
            }
        }
    }

    open fun pause() {
        timerJob?.cancel()
        timerJob = null
        alreadyStarted.set(false)
    }

    open fun stop() {
        scope.launch {
            timerJob?.cancel()
            timerJob?.join()
            timerJob = null
            gameStatusRepository.saveTimeCounter(null)
        }
    }

    private suspend fun startTimeCounter() {
        while (true) {
            delay(ONE_SECOND_IN_MILLIS)
            updateSeconds((_timeCounterState.value ?: 0L) + 1)
        }
    }

    private fun updateSeconds(seconds: Long) {
        // We don't use the Flow returned by the data store so we separate when
        // the time is updated on UI and when it is actually updated on disk.
        // By now, we are writing updated seconds to disk every second, we might need to
        // do some throttle if we observe that performance is affected by it.
        _timeCounterState.value = seconds
        gameStatusRepository.saveTimeCounter(seconds)
    }

    private inline fun onStarted(block: () -> Unit) {
        if (alreadyStarted.compareAndSet(false, true)) {
            block()
        }
    }

    private companion object {
        const val ONE_SECOND_IN_MILLIS = 1_000L
    }
}
