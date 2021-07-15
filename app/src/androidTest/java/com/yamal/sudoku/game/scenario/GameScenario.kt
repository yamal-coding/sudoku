package com.yamal.sudoku.game.scenario

import com.yamal.sudoku.game.domain.DoNotShowSetUpNewGameHintAgain
import com.yamal.sudoku.model.Board
import com.yamal.sudoku.model.ReadOnlyBoard
import com.yamal.sudoku.model.SudokuCellValue
import javax.inject.Inject

class GameScenario @Inject constructor(
    private val doNotShowSetUpNewGameHintAgain: DoNotShowSetUpNewGameHintAgain
) {
    fun givenANewGame(withAGivenSudoku: ReadOnlyBoard) {
        TODO()
    }

    fun givenASavedGame(withAGivenSudoku: ReadOnlyBoard) {
        TODO()
    }

    fun givenThatSetUpNewGameHintWillNotBeDisplayed() {
        doNotShowSetUpNewGameHintAgain()
    }

    companion object {
        const val SOME_ROW = 2
        const val SOME_COLUMN = 2
        val SOME_CELL_VALUE = SudokuCellValue.SEVEN
        val SOME_BOARD: ReadOnlyBoard = Board.empty().apply {
            selectCell(SOME_ROW - 1, SOME_COLUMN - 1)
            fixSelectedCell(SudokuCellValue.SEVEN)
        }

        const val ROW_OF_REMAINING_CELL = 2
        const val COLUMN_OF_REMAINING_CELL = 2
        val SOME_REMAINING_CELL_VALE = SudokuCellValue.NINE
        val SOME_ALMOST_FULL_BOARD: ReadOnlyBoard = Board.almostDone()
    }
}