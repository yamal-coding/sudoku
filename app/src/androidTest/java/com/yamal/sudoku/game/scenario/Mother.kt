package com.yamal.sudoku.game.scenario

import com.yamal.sudoku.game.status.data.storage.model.BoardDO
import com.yamal.sudoku.game.status.data.storage.model.DifficultyDO
import com.yamal.sudoku.game.status.data.storage.model.SudokuCellDO

object SudokuMother {
    fun someBoardDO(): BoardDO =
        BoardDO(
            cells = notFixedDOCells(
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
                it.add(SudokuCellDO(0, isFixed = true))
            },
            difficulty = DifficultyDO.EASY,
        )

    private fun notFixedDOCells(vararg cellValues: Int): MutableList<SudokuCellDO> =
        cellValues.map { SudokuCellDO(value = it, isFixed = false) }.toMutableList()
}