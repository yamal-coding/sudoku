package com.yamal.sudoku.game.scenario

import com.yamal.sudoku.game.status.domain.DoNotShowSetUpNewGameHintAgain
import com.yamal.sudoku.game.status.domain.SaveBoard
import com.yamal.sudoku.model.Board
import com.yamal.sudoku.model.ReadOnlyBoard
import com.yamal.sudoku.model.SudokuCellValue
import com.yamal.sudoku.test.mocks.FakeBoardsDataSourceImpl
import javax.inject.Inject

class GameScenario @Inject constructor(
    private val doNotShowSetUpNewGameHintAgain: DoNotShowSetUpNewGameHintAgain,
    private val fakeBoardsDataSourceImpl: FakeBoardsDataSourceImpl,
    private val saveBoard: SaveBoard,
) {
    fun givenANewGame(withAGivenSudoku: ReadOnlyBoard) {
        fakeBoardsDataSourceImpl.whenGettingNewBoard(thenReturn = withAGivenSudoku)
    }

    fun givenASavedGame(withAGivenSudoku: ReadOnlyBoard) {
        saveBoard(withAGivenSudoku)
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

        const val ROW_OF_REMAINING_CELL = 9
        const val COLUMN_OF_REMAINING_CELL = 9
        val SOME_REMAINING_CELL_VALE = SudokuCellValue.NINE
        val SOME_ALMOST_FULL_BOARD: ReadOnlyBoard = Board.almostDone()
    }
}