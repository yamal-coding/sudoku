package com.yamal.sudoku.commons.utils

import com.yamal.sudoku.game.domain.BOARD_SIDE
import com.yamal.sudoku.model.SudokuCell

operator fun List<SudokuCell>.get(row: Int, column: Int): SudokuCell =
    getAsSudokuBoard(row, column)

operator fun MutableList<SudokuCell>.set(row: Int, column: Int, value: SudokuCell) {
    setAsSudokuBoard(row, column, value)
}

fun <T> List<T>.getAsSudokuBoard(row: Int, column: Int): T =
    this[(row * BOARD_SIDE) + column]

fun <T> MutableList<T>.setAsSudokuBoard(row: Int, column: Int, value: T) {
    this[(row * BOARD_SIDE) + column] = value
}