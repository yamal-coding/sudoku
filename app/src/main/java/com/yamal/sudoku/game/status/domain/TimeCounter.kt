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
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
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

    private var currentState = TimeCounterState.IDLE
    private val stateMutex = Mutex()

    open fun start(initialSeconds: Long) {
        onState(expect = TimeCounterState.IDLE, update = TimeCounterState.RESUMED) {
            timerJob = scope.launch {
                updateSeconds(initialSeconds)
                startTimeCounter()
            }
        }
    }

    open fun resume() {
        onState(expect = TimeCounterState.PAUSED, update = TimeCounterState.RESUMED) {
            timerJob = scope.launch {
                startTimeCounter()
            }
        }
    }

    open fun pause() {
        onState(expect = TimeCounterState.RESUMED, update = TimeCounterState.PAUSED) {
            timerJob?.cancel()
            timerJob = null
        }
    }

    open fun stop() {
        onState(expect = TimeCounterState.RESUMED, update = TimeCounterState.STOPPED) {
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

    private inline fun onState(
        expect: TimeCounterState,
        update: TimeCounterState,
        crossinline block: suspend () -> Unit
    ) {
        scope.launch {
            stateMutex.withLock {
                if (currentState == expect) {
                    currentState = update
                    block()
                }
            }
        }
    }

    private companion object {
        const val ONE_SECOND_IN_MILLIS = 1_000L
    }

    private enum class TimeCounterState {
        IDLE,
        PAUSED,
        RESUMED,
        STOPPED
    }
}
