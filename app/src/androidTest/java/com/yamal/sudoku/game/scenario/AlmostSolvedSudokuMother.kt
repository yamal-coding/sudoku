package com.yamal.sudoku.game.scenario

import com.yamal.sudoku.game.status.data.storage.model.BoardDO
import com.yamal.sudoku.game.status.data.storage.model.DifficultyDO
import com.yamal.sudoku.game.status.data.storage.model.SudokuCellDO
import com.yamal.sudoku.model.SudokuCellValue

object AlmostSolvedSudokuMother {
    fun someAlmostSolvedBoardDO(
        difficulty: String = DifficultyDO.EASY,
    ): BoardDO =
        BoardDO(
            cells = fixedDOCells(
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
                it.add(SudokuCellDO(0, isFixed = false))
            },
            difficulty = difficulty,
        )

    fun getRemainingCellCoordinates(): Pair<Int, Int> = 8 to 8

    fun getRemainingCellValue(): SudokuCellValue = SudokuCellValue.NINE

    fun getWrongRemainingCellValue(): SudokuCellValue = SudokuCellValue.SEVEN

    private fun fixedDOCells(vararg cellValues: Int): MutableList<SudokuCellDO> =
        cellValues.map { SudokuCellDO(value = it, isFixed = true) }.toMutableList()
}