package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.game.status.data.GameStatusRepository
import com.yamal.sudoku.model.SudokuCellValue
import javax.inject.Inject

open class AddOrRemovePossibilityToSelectedCell @Inject constructor(
    private val currentGame: CurrentGame,
    private val repository: GameStatusRepository,
) {
    open operator fun invoke(value: SudokuCellValue) {
        if (!currentGame.hasFinished()) {
            currentGame.onPossibilityAddedOrRemoved(value)
            repository.saveBoard(currentGame.getCurrentBoard())
        }
    }
}
