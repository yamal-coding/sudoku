package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.game.status.data.GameStatusRepository
import com.yamal.sudoku.model.SudokuCellValue
import com.yamal.sudoku.stats.domain.UpdateGameFinishedStatistics
import javax.inject.Inject

open class UpdateSelectedCell @Inject constructor(
    private val currentGame: CurrentGame,
    private val repository: GameStatusRepository,
    private val timeCounter: TimeCounter,
    private val updateStatistics: UpdateGameFinishedStatistics,
) {
    open operator fun invoke(value: SudokuCellValue) {
        if (!currentGame.hasFinished()) {
            currentGame.onCellUpdated(value)
            if (currentGame.hasFinished()) {
                onGameFinished()
            } else {
                repository.saveBoard(currentGame.getCurrentBoard())
            }
        }
    }

    private fun onGameFinished() {
        timeCounter.stop()
        updateStatistics(
            difficulty = currentGame.getCurrentBoard().difficulty,
            gameTimeInSeconds = timeCounter.getCurrentTime(),
        )
        repository.removeSavedBoard()
        currentGame.onGameFinished()
    }
}
