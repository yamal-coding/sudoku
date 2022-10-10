package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.game.status.data.GameStatusRepository
import javax.inject.Inject

open class ClearBoard @Inject constructor(
    private val currentGame: CurrentGame,
    private val repository: GameStatusRepository,
) {
    open operator fun invoke() {
        if (!currentGame.hasFinished()) {
            currentGame.onClearBoard()
            repository.saveBoard(currentGame.getCurrentBoard())
        }
    }
}
