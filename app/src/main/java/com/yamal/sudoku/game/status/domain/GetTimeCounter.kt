package com.yamal.sudoku.game.status.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTimeCounter @Inject constructor(
    private val timeCounter: TimeCounter,
) {
    operator fun invoke(): Flow<Long?> =
        timeCounter.timeCounterState
}
