package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.game.status.data.GameStatusRepository
import javax.inject.Inject

class RemoveSavedBoard @Inject constructor(
    private val repository: GameStatusRepository
) {
    operator fun invoke() {
        repository.removeSavedBoard()
    }
}