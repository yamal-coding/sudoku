package com.yamal.sudoku.storage.model

data class SudokuCellDO(
    val value: Int,
    val isFixed: Boolean
)

data class BoardDO(val cells: List<List<SudokuCellDO>>)
