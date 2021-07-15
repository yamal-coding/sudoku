package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.game.status.data.GameStatusRepository
import javax.inject.Inject

class DoNotShowSetUpNewGameHintAgain @Inject constructor(
    private val repository: GameStatusRepository
) {
    operator fun invoke() {
        repository.doNotShowSetUpNewGameHintAgain()
    }
}