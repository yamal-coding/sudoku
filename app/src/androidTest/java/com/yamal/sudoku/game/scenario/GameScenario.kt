package com.yamal.sudoku.game.scenario

import com.yamal.sudoku.game.domain.Board
import com.yamal.sudoku.game.status.domain.SaveBoard
import com.yamal.sudoku.model.SudokuCellValue
import com.yamal.sudoku.test.mocks.FakeLevelsDataSourceImpl
import javax.inject.Inject

class GameScenario @Inject constructor(
    private val fakeBoardsDataSourceImpl: FakeLevelsDataSourceImpl,
    private val saveBoard: SaveBoard,
) {
    fun givenANewGame(withAGivenSudoku: Board) {
        //fakeBoardsDataSourceImpl.whenGettingNewBoard(thenReturn = withAGivenSudoku)
    }

    fun givenASavedGame(withAGivenSudoku: Board) {
        saveBoard(withAGivenSudoku)
    }

    companion object {
        const val ROW_OF_REMAINING_CELL = 9
        const val COLUMN_OF_REMAINING_CELL = 9
        val SOME_REMAINING_CELL_VALE = SudokuCellValue.NINE
        val SOME_ALMOST_FULL_BOARD: Board = Board.empty() // TODO fix
    }
}