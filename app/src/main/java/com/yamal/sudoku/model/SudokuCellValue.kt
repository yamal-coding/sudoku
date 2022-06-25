package com.yamal.sudoku.model

@Suppress("MagicNumber")
enum class SudokuCellValue(private val label: String, val intValue: Int) {
    ONE("1", 1),
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    EMPTY("", 0);

    override fun toString(): String = label
}