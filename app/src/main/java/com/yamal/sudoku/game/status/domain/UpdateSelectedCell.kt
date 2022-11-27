package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.game.status.data.GameStatusRepository
import com.yamal.sudoku.model.SudokuCellValue
import javax.inject.Inject

open class UpdateSelectedCell @Inject constructor(
    private val currentGame: CurrentGame,
    private val repository: GameStatusRepository,
    private val timeCounter: TimeCounter,
) {
    open operator fun invoke(value: SudokuCellValue) {
        if (!currentGame.hasFinished()) {
            currentGame.onCellUpdated(value)
            if (currentGame.hasFinished()) {
                timeCounter.stop()
                repository.removeSavedBoard()
                currentGame.onGameFinished()
            } else {
                repository.saveBoard(currentGame.getCurrentBoard())
            }
        }
    }
}
