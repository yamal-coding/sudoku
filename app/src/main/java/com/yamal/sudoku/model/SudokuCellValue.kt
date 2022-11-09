package com.yamal.sudoku.model

enum class SudokuCellValue(private val label: String) {
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    EMPTY("");

    override fun toString(): String = label
}