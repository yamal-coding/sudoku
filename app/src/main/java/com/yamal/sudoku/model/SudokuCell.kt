package com.yamal.sudoku.model

data class SudokuCell(
    var value: SudokuCellValue,
    var isFixed: Boolean
)