package com.yamal.sudoku.model

data class SudokuCell(
    val value: SudokuCellValue,
    val isFixed: Boolean,
    val possibilities: Set<SudokuCellValue>? = null,
)
