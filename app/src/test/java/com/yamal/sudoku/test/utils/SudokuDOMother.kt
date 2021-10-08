package com.yamal.sudoku.test.utils

import com.yamal.sudoku.model.Board
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.model.SudokuCell
import com.yamal.sudoku.model.SudokuCellValue
import com.yamal.sudoku.storage.model.BoardDO
import com.yamal.sudoku.storage.model.DifficultyDO
import com.yamal.sudoku.storage.model.SudokuCellDO

object SudokuDOMother {
    val SOME_BOARD = BoardDO(
        cells = listOf(listOf(SudokuCellDO(value = 1, isFixed = false))),
        difficulty = null
    )

    fun someEasyBoardWithExpectedDOModel(): Pair<Board, BoardDO> =
        someBoardWithExpectedDOModel(Difficulty.EASY, DifficultyDO.EASY)

    fun someMediumBoardWithExpectedDOModel(): Pair<Board, BoardDO> =
        someBoardWithExpectedDOModel(Difficulty.MEDIUM, DifficultyDO.MEDIUM)

    fun someHardBoardWithExpectedDOModel(): Pair<Board, BoardDO> =
        someBoardWithExpectedDOModel(Difficulty.HARD, DifficultyDO.HARD)

    fun someUnknownBoardWithExpectedDOModelWithNullDifficulty(): Pair<Board, BoardDO> =
        someBoardWithExpectedDOModel(Difficulty.UNKNOWN, null)

    fun someUnknownBoardWithExpectedDOModelWithInvalidDifficulty(): Pair<Board, BoardDO> =
        someBoardWithExpectedDOModel(Difficulty.UNKNOWN, null)

    private fun someBoardWithExpectedDOModel(domainDifficulty: Difficulty, doDifficulty: String?): Pair<Board, BoardDO> {
        val board = Board(
            cells = listOf(
                Board.rowOf(5, 3, 4, 6, 7, 8, 9, 1, 2),
                Board.rowOf(6, 7, 2, 1, 9, 5, 3, 4, 8),
                Board.rowOf(1, 9, 8, 3, 4, 2, 5, 6, 7),
                Board.rowOf(8, 5, 9, 7, 6, 1, 4, 2, 3),
                Board.rowOf(4, 2, 6, 8, 5, 3, 7, 9, 1),
                Board.rowOf(7, 1, 3, 9, 2, 4, 8, 5, 6),
                Board.rowOf(9, 6, 1, 5, 3, 7, 2, 8, 4),
                Board.rowOf(2, 8, 7, 4, 1, 9, 6, 3, 5),
                Board.rowOf(3, 4, 5, 2, 8, 6, 1, 7).also {
                    it.add(SudokuCell(SudokuCellValue.EMPTY, isFixed = true))
                }
            ),
            difficulty = domainDifficulty
        )

        val expectedBoardDO = BoardDO(
            cells = listOf(
                rowDOOf(5, 3, 4, 6, 7, 8, 9, 1, 2),
                rowDOOf(6, 7, 2, 1, 9, 5, 3, 4, 8),
                rowDOOf(1, 9, 8, 3, 4, 2, 5, 6, 7),
                rowDOOf(8, 5, 9, 7, 6, 1, 4, 2, 3),
                rowDOOf(4, 2, 6, 8, 5, 3, 7, 9, 1),
                rowDOOf(7, 1, 3, 9, 2, 4, 8, 5, 6),
                rowDOOf(9, 6, 1, 5, 3, 7, 2, 8, 4),
                rowDOOf(2, 8, 7, 4, 1, 9, 6, 3, 5),
                rowDOOf(3, 4, 5, 2, 8, 6, 1, 7).also {
                    it.add(SudokuCellDO(0, isFixed = true))
                }
            ),
            difficulty = doDifficulty
        )

        return board to expectedBoardDO
    }

    private fun rowDOOf(vararg cellValues: Int): MutableList<SudokuCellDO> =
        cellValues.map { SudokuCellDO(value = it, isFixed = false) }.toMutableList()
}