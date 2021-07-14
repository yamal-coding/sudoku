package com.yamal.sudoku.game.scenario

import com.yamal.sudoku.game.domain.DoNotShowSetUpNewGameHintAgain
import com.yamal.sudoku.model.Board
import com.yamal.sudoku.model.ReadOnlyBoard
import com.yamal.sudoku.model.SudokuCellValue
import javax.inject.Inject

class SetUpGameScenario @Inject constructor(
    private val doNotShowSetUpNewGameHintAgain: DoNotShowSetUpNewGameHintAgain
) {
    fun givenThatSetUpNewGameHintWillNotBeDisplayed() {
        doNotShowSetUpNewGameHintAgain()
    }

    companion object {
        const val SOME_ROW = 2
        const val SOME_COLUMN = 2
        val SOME_BOARD: ReadOnlyBoard = Board.empty().apply {
            selectCell(SOME_ROW - 1, SOME_COLUMN - 1)
            fixSelectedCell(SudokuCellValue.SEVEN)
        }
    }
}