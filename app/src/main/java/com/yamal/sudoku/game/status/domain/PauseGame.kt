package com.yamal.sudoku.game.status.domain

import javax.inject.Inject

class PauseGame @Inject constructor(
    private val timeCounter: TimeCounter,
) {
    operator fun invoke() {
        timeCounter.pause()
    }
}
