package com.yamal.sudoku.test.utils

import com.yamal.sudoku.model.Board
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.model.SudokuCell
import com.yamal.sudoku.model.SudokuCellValue

object SolvedSudokuMother {
    fun solvedSudoku(): Board =
        Board(
            cells = listOf(
                Board.rowOf(5, 3, 4, 6, 7, 8, 9, 1, 2),
                Board.rowOf(6, 7, 2, 1, 9, 5, 3, 4, 8),
                Board.rowOf(1, 9, 8, 3, 4, 2, 5, 6, 7),
                Board.rowOf(8, 5, 9, 7, 6, 1, 4, 2, 3),
                Board.rowOf(4, 2, 6, 8, 5, 3, 7, 9, 1),
                Board.rowOf(7, 1, 3, 9, 2, 4, 8, 5, 6),
                Board.rowOf(9, 6, 1, 5, 3, 7, 2, 8, 4),
                Board.rowOf(2, 8, 7, 4, 1, 9, 6, 3, 5),
                Board.rowOf(3, 4, 5, 2, 8, 6, 1, 7, 9)
            ),
            difficulty = Difficulty.UNKNOWN
        )

    fun solvedSudokuAsMap(): List<List<Int>> =
        listOf(
            listOf(5, 3, 4, 6, 7, 8, 9, 1, 2),
            listOf(6, 7, 2, 1, 9, 5, 3, 4, 8),
            listOf(1, 9, 8, 3, 4, 2, 5, 6, 7),
            listOf(8, 5, 9, 7, 6, 1, 4, 2, 3),
            listOf(4, 2, 6, 8, 5, 3, 7, 9, 1),
            listOf(7, 1, 3, 9, 2, 4, 8, 5, 6),
            listOf(9, 6, 1, 5, 3, 7, 2, 8, 4),
            listOf(2, 8, 7, 4, 1, 9, 6, 3, 5),
            listOf(3, 4, 5, 2, 8, 6, 1, 7, 9)
        )
}

object AlmostSolvedSudokuMother {
    fun almostSolvedSudoku(): Board =
        Board(
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
                    it.add(SudokuCell(SudokuCellValue.EMPTY, isFixed = false))
                }
            ),
            difficulty = Difficulty.UNKNOWN
        )

    fun getEmptyCellCoordinates(): Pair<Int, Int> = 8 to 8

    fun getRemainingCellValue(): SudokuCellValue = SudokuCellValue.NINE

    fun getWrongRemainingCellValue(): SudokuCellValue = SudokuCellValue.SEVEN
}