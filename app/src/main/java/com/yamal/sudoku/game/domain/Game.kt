package com.yamal.sudoku.game.domain

import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.model.SudokuCell
import com.yamal.sudoku.model.SudokuCellValue

const val BOARD_SIDE = 9
const val QUADRANTS_PER_SIDE = 9

operator fun List<SudokuCell>.get(row: Int, col: Int): SudokuCell =
    this[(row * BOARD_SIDE) + col]

data class NewTypeBoard(
    val cells: List<SudokuCell>
)

class Game {
    private val occurrencesOfEachValuePerRow = occurrencesOfEachValuePerArea()
    private val occurrencesOfEachValuePerColumn = occurrencesOfEachValuePerArea()
    private val occurrencesOfEachValuePerQuadrant = occurrencesOfEachValuePerArea()

    private lateinit var board: NewTypeBoard

    val currentBoard: NewTypeBoard
        get() = board.copy()
    lateinit var difficulty: Difficulty
        private set

    fun start(
        board: NewTypeBoard,
        difficulty: Difficulty
    ) {
        this.board = board
        this.difficulty = difficulty

        for (row in 0 until BOARD_SIDE) {
            for (col in 0 until BOARD_SIDE) {
                val cell = board.cells[row, col]

                if (cell.value != SudokuCellValue.EMPTY) {
                    val cellValue = cell.value
                    occurrencesOfEachValuePerRow[row][cellValue] = occurrencesOfEachValuePerRow[row][cellValue]!! + 1
                    occurrencesOfEachValuePerColumn[col][cellValue] = occurrencesOfEachValuePerColumn[col][cellValue]!! + 1
                    occurrencesOfEachValuePerQuadrant[quadrantByRowAndColumn(row, col)][cellValue] =
                        occurrencesOfEachValuePerQuadrant[quadrantByRowAndColumn(row, col)][cellValue]!! + 1
                }
            }
        }
    }

    fun setSelectedCell(row: Int, column: Int, newValue: SudokuCellValue) {
        val previousValue = board.cells[row, column].value
        if (previousValue != SudokuCellValue.EMPTY) {
            decreaseOccurrencesOfValue(row, column, previousValue)
        }

        if (newValue != SudokuCellValue.EMPTY) {
            increaseOccurrencesOfValue(row, column, newValue)
        }

        board.cells[row, column].value = newValue
    }

    fun isSolved(): Boolean {
        val rowsAreOk = occurrencesOfEachValuePerRow.all { rowMap ->
            rowMap.all { cellEntry -> cellEntry.value == 1 }
        }

        val columnsAreOk = occurrencesOfEachValuePerColumn.all { columnMap ->
            columnMap.all { cellEntry -> cellEntry.value == 1 }
        }

        val quadrantsAreOk = occurrencesOfEachValuePerColumn.all { quadrantMap ->
            quadrantMap.all { cellEntry -> cellEntry.value == 1 }
        }

        return rowsAreOk && columnsAreOk && quadrantsAreOk
    }

    private fun occurrencesOfEachValuePerArea(): List<MutableMap<SudokuCellValue, Int>> =
        (0 until BOARD_SIDE).map {
            val occurrencesOfValues = mutableMapOf<SudokuCellValue, Int>()

            SudokuCellValue.values().forEach {
                if (it != SudokuCellValue.EMPTY) {
                    occurrencesOfValues[it] = 0
                }
            }

            occurrencesOfValues
        }

    private fun quadrantByRowAndColumn(row: Int, column: Int): Int {
        val quadrantForRow = row / QUADRANTS_PER_SIDE
        val quadrantForColumn = column / QUADRANTS_PER_SIDE
        return quadrantForRow * quadrantForColumn
    }

    private fun decreaseOccurrencesOfValue(row: Int, column: Int, value: SudokuCellValue) {
        occurrencesOfEachValuePerRow[row][value] = occurrencesOfEachValuePerRow[row][value]!! - 1
        occurrencesOfEachValuePerColumn[column][value] = occurrencesOfEachValuePerColumn[column][value]!! - 1
        occurrencesOfEachValuePerQuadrant[quadrantByRowAndColumn(row, column)][value] =
            occurrencesOfEachValuePerQuadrant[quadrantByRowAndColumn(row, column)][value]!! - 1
    }

    private fun increaseOccurrencesOfValue(row: Int, column: Int, value: SudokuCellValue) {
        occurrencesOfEachValuePerRow[row][value] = occurrencesOfEachValuePerRow[row][value]!! + 1
        occurrencesOfEachValuePerColumn[column][value] = occurrencesOfEachValuePerColumn[column][value]!! + 1
        occurrencesOfEachValuePerQuadrant[quadrantByRowAndColumn(row, column)][value] =
            occurrencesOfEachValuePerQuadrant[quadrantByRowAndColumn(row, column)][value]!! + 1
    }
}
