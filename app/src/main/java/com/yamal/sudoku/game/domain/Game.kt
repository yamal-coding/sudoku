package com.yamal.sudoku.game.domain

import com.yamal.sudoku.model.SudokuCell
import com.yamal.sudoku.model.SudokuCellValue
import com.yamal.sudoku.commons.utils.get
import com.yamal.sudoku.commons.utils.set
import com.yamal.sudoku.model.Difficulty
import java.util.Stack

const val BOARD_SIDE = 9
const val QUADRANTS_PER_SIDE = 3

interface ReadOnlyBoard {
    val difficulty: Difficulty
    operator fun get(row: Int, col: Int): SudokuCell
}

data class Board(
    private val cells: MutableList<SudokuCell>,
    override val difficulty: Difficulty,
) : ReadOnlyBoard {

    override fun get(row: Int, col: Int): SudokuCell =
        cells[row, col]

    operator fun set(row: Int, col: Int, value: SudokuCellValue) {
        cells[row, col] = cells[row, col].copy(value = value)
    }

    fun copy(): Board =
        Board(
            mutableListOf<SudokuCell>().also { it.addAll(cells) },
            difficulty = difficulty
        )

    companion object {
        fun empty(difficulty: Difficulty): Board =
            Board(
                mutableListOf<SudokuCell>().apply {
                    repeat(BOARD_SIDE * BOARD_SIDE) {
                        add(SudokuCell(
                            value = SudokuCellValue.EMPTY,
                            isFixed = false
                        ))
                    }
                },
                difficulty = difficulty
            )
    }
}

private data class Movement(
    val row: Int,
    val column: Int,
    val previousValue: SudokuCellValue,
    val newValue: SudokuCellValue,
)

class Game(
    private val board: Board,
) {
    private val occurrencesOfEachValuePerRow = initOccurrencesOfEachValuePerLine()
    private val occurrencesOfEachValuePerColumn = initOccurrencesOfEachValuePerLine()
    private val occurrencesOfEachValuePerQuadrant = initOccurrencesOfEachValuePerQuadrant()

    var selectedRow: Int? = null
        private set
    var selectedColumn: Int? = null
        private set

    private val movementsDone = Stack<Movement>()

    val canUndo: Boolean
        get() = movementsDone.isNotEmpty()

    val currentBoard: ReadOnlyBoard
        get() = board.copy()

    init {
        for (row in 0 until BOARD_SIDE) {
            for (col in 0 until BOARD_SIDE) {
                val cell = board[row, col]

                if (cell.value != SudokuCellValue.EMPTY) {
                    val cellValue = cell.value
                    occurrencesOfEachValuePerRow[row][cellValue] =
                        occurrencesOfEachValuePerRow[row][cellValue]!! + 1
                    occurrencesOfEachValuePerColumn[col][cellValue] =
                        occurrencesOfEachValuePerColumn[col][cellValue]!! + 1
                    val quadrant = quadrantByRowAndColumn(row, col)
                    occurrencesOfEachValuePerQuadrant[quadrant][cellValue] =
                        occurrencesOfEachValuePerQuadrant[quadrant][cellValue]!! + 1
                }
            }
        }
    }

    fun selectCell(row: Int, column: Int): Boolean =
        if (!board[row, column].isFixed) {
            selectedRow = row
            selectedColumn = column
            true
        } else {
            false
        }

    fun setSelectedCell(newValue: SudokuCellValue) {
        val row = selectedRow
        val column = selectedColumn
        if (row != null && column != null && !board[row, column].isFixed) {
            val movement = Movement(
                row = row,
                column = column,
                previousValue = board[row, column].value,
                newValue = newValue
            )

            updateCell(movement)
            registerMovement(movement)
        }
    }

    private fun updateCell(movement: Movement) {
        with (movement) {
            if (previousValue != SudokuCellValue.EMPTY) {
                decreaseOccurrencesOfValue(row, column, previousValue)
            }

            if (newValue != SudokuCellValue.EMPTY) {
                increaseOccurrencesOfValue(row, column, newValue)
            }

            board[row, column] = newValue
        }
    }

    private fun registerMovement(movement: Movement) {
        if (movementsDone.isEmpty() || movementsDone.peek() != movement) {
            movementsDone.push(movement)
        }
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

    fun undo() {
        if (canUndo) {
            val lastMovement = movementsDone.pop()

            updateCell(lastMovement.copy(
                previousValue = lastMovement.newValue,
                newValue = lastMovement.previousValue
            ))
        }
    }

    private fun initOccurrencesOfEachValuePerLine(): List<MutableMap<SudokuCellValue, Int>> =
        (0 until BOARD_SIDE).map {
            val occurrencesOfValues = mutableMapOf<SudokuCellValue, Int>()

            SudokuCellValue.values().forEach {
                if (it != SudokuCellValue.EMPTY) {
                    occurrencesOfValues[it] = 0
                }
            }

            occurrencesOfValues
        }

    private fun initOccurrencesOfEachValuePerQuadrant(): List<List<MutableMap<SudokuCellValue, Int>>> =
        (0 until QUADRANTS_PER_SIDE).map {
            (0 until QUADRANTS_PER_SIDE).map {
                val occurrencesOfValues = mutableMapOf<SudokuCellValue, Int>()

                SudokuCellValue.values().forEach {
                    if (it != SudokuCellValue.EMPTY) {
                        occurrencesOfValues[it] = 0
                    }
                }

                occurrencesOfValues
            }
        }

    private fun quadrantByRowAndColumn(row: Int, column: Int): Pair<Int, Int> {
        val quadrantRow = row / QUADRANTS_PER_SIDE
        val quadrantColumn = column / QUADRANTS_PER_SIDE
        return quadrantRow to quadrantColumn
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

    private operator fun List<List<MutableMap<SudokuCellValue, Int>>>.get(
        position: Pair<Int, Int>
    ): MutableMap<SudokuCellValue, Int> =
        this[position.first][position.second]
}
