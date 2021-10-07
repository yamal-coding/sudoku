package com.yamal.sudoku.test.utils

import com.yamal.sudoku.storage.model.BoardDO
import com.yamal.sudoku.storage.model.SudokuCellDO

object SudokuDOMother {
    val SOME_BOARD = BoardDO(
        cells = listOf(listOf(SudokuCellDO(value = 1, isFixed = false))),
        difficulty = null
    )
}