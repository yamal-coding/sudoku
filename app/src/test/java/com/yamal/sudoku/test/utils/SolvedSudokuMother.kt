package com.yamal.sudoku.test.utils

import com.yamal.sudoku.game.domain.Board
import com.yamal.sudoku.game.status.data.toSudokuCell
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.model.SudokuCell
import com.yamal.sudoku.model.SudokuCellValue

object SudokuUtils {
    fun notFixedCells(vararg items: Int): MutableList<SudokuCell> =
        items.map { SudokuCell(it.toSudokuCell(), isFixed = false) }.toMutableList()

    fun fixedCells(vararg items: Int): MutableList<SudokuCell> =
        items.map { SudokuCell(it.toSudokuCell(), isFixed = true) }.toMutableList()
}

object SolvedSudokuMother {
    fun solvedSudoku(): Board =
        Board(
            cells = SudokuUtils.notFixedCells(
                5, 3, 4, 6, 7, 8, 9, 1, 2,
                6, 7, 2, 1, 9, 5, 3, 4, 8,
                1, 9, 8, 3, 4, 2, 5, 6, 7,
                8, 5, 9, 7, 6, 1, 4, 2, 3,
                4, 2, 6, 8, 5, 3, 7, 9, 1,
                7, 1, 3, 9, 2, 4, 8, 5, 6,
                9, 6, 1, 5, 3, 7, 2, 8, 4,
                2, 8, 7, 4, 1, 9, 6, 3, 5,
                3, 4, 5, 2, 8, 6, 1, 7, 9
            ),
            difficulty = Difficulty.EASY,
        )

    fun solvedSudokuAsMap(): List<Int> =
        listOf(
            5, 3, 4, 6, 7, 8, 9, 1, 2,
            6, 7, 2, 1, 9, 5, 3, 4, 8,
            1, 9, 8, 3, 4, 2, 5, 6, 7,
            8, 5, 9, 7, 6, 1, 4, 2, 3,
            4, 2, 6, 8, 5, 3, 7, 9, 1,
            7, 1, 3, 9, 2, 4, 8, 5, 6,
            9, 6, 1, 5, 3, 7, 2, 8, 4,
            2, 8, 7, 4, 1, 9, 6, 3, 5,
            3, 4, 5, 2, 8, 6, 1, 7, 9
        )
}

object AlmostSolvedSudokuMother {

    fun almostSolvedSudoku(): Board =
        Board(
            cells = SudokuUtils.fixedCells(
                5, 3, 4, 6, 7, 8, 9, 1, 2,
                6, 7, 2, 1, 9, 5, 3, 4, 8,
                1, 9, 8, 3, 4, 2, 5, 6, 7,
                8, 5, 9, 7, 6, 1, 4, 2, 3,
                4, 2, 6, 8, 5, 3, 7, 9, 1,
                7, 1, 3, 9, 2, 4, 8, 5, 6,
                9, 6, 1, 5, 3, 7, 2, 8, 4,
                2, 8, 7, 4, 1, 9, 6, 3, 5,
                3, 4, 5, 2, 8, 6, 1, 7
            ).also {
                it.add(SudokuCell(SudokuCellValue.EMPTY, isFixed = false))
            },
            difficulty = Difficulty.EASY,
        )

    const val almostSolvedSudokuAsRawLevel: String =
        "534678912672195348198342567859761423426853791713924856961537284287419635345286170"

    fun getEmptyCellCoordinates(): Pair<Int, Int> = 8 to 8

    fun getRemainingCellValue(): SudokuCellValue = SudokuCellValue.NINE

    fun getWrongRemainingCellValue(): SudokuCellValue = SudokuCellValue.SEVEN
}