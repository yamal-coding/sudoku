package com.yamal.sudoku.game.domain

import com.yamal.sudoku.model.SudokuCellValue
import java.util.Stack

class Game(
    val id: String,
    private val board: Board,
) {
    private lateinit var occurrencesOfEachValuePerRow: List<MutableMap<SudokuCellValue, Int>>
    private lateinit var occurrencesOfEachValuePerColumn: List<MutableMap<SudokuCellValue, Int>>
    private lateinit var occurrencesOfEachValuePerQuadrant: List<List<MutableMap<SudokuCellValue, Int>>>

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
        init()
    }

    private fun init() {
        occurrencesOfEachValuePerRow = initOccurrencesOfEachValuePerLine()
        occurrencesOfEachValuePerColumn = initOccurrencesOfEachValuePerLine()
        occurrencesOfEachValuePerQuadrant = initOccurrencesOfEachValuePerQuadrant()

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
        onValidSelectedCell { row, column ->
            val previousCell = board[row, column]
            val previousCellState =
                if (previousCell.value == SudokuCellValue.EMPTY
                    && !previousCell.possibilities.isNullOrEmpty()
                ) {
                    PreviousCellState.Possibilities(
                        possibilities = previousCell.possibilities.map { it }.toSet()
                    )
                } else {
                    PreviousCellState.SingleValue(previousCell.value)
                }

            val movement = SetValueMovement(
                row = row,
                column = column,
                previousCellState = previousCellState,
                newValue = newValue
            )

            updateCell(movement)
            registerMovement(movement)
        }
    }

    fun addOrRemovePossibleValue(possibleValue: SudokuCellValue) {
        onValidSelectedCell { row, column ->
            if (possibleValue == SudokuCellValue.EMPTY) {
                setSelectedCell(possibleValue)
            } else {
                val previousValue = board[row, column].value.takeIf { it != SudokuCellValue.EMPTY }
                val shouldRemovePossibility =
                    board[row, column].possibilities?.contains(possibleValue) == true

                val movement = SetPossibilitiesMovement(
                    row = row,
                    column = column,
                    previousCellValue = previousValue,
                    newPossibilities = setOf(possibleValue),
                    shouldAddPossibilities = !shouldRemovePossibility
                )

                updateCell(movement)
                registerMovement(movement)
            }
        }
    }

    private inline fun onValidSelectedCell(block: (row: Int, column: Int) -> Unit) {
        val row = selectedRow
        val column = selectedColumn
        if (row != null && column != null && !board[row, column].isFixed) {
            block(row, column)
        }
    }

    private fun updateCell(movement: SetValueMovement) {
        with (movement) {
            if (previousCellState is PreviousCellState.SingleValue
                && previousCellState.value != SudokuCellValue.EMPTY) {
                decreaseOccurrencesOfValue(row, column, previousCellState.value)
            }

            if (newValue != SudokuCellValue.EMPTY) {
                increaseOccurrencesOfValue(row, column, newValue)
            }

            board[row, column] = newValue
        }
    }

    private fun updateCell(movement: SetPossibilitiesMovement) {
        with (movement) {
            if (previousCellValue != null && previousCellValue != SudokuCellValue.EMPTY) {
                decreaseOccurrencesOfValue(row, column, previousCellValue)
                board[row, column] = SudokuCellValue.EMPTY
            }

            if (movement.shouldAddPossibilities) {
                board.addPossibilities(row, column, movement.newPossibilities)
            } else {
                board.removePossibilities(row, column, movement.newPossibilities)
            }
        }
    }

    private fun registerMovement(movement: Movement) {
        val newValueIsTheSameAsPreviousOne =  when (movement) {
            is SetValueMovement -> {
                movement.previousCellState is PreviousCellState.SingleValue &&
                        movement.previousCellState.value == movement.newValue
            }
            is SetPossibilitiesMovement -> {
                false
            }
        }

        if (!newValueIsTheSameAsPreviousOne) {
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
            when (val lastMovement = movementsDone.pop()) {
                is SetValueMovement -> undoSetValueMovement(lastMovement)
                is SetPossibilitiesMovement -> undoSetPossibilitiesMovement(lastMovement)
            }
        }
    }

    private fun undoSetValueMovement(movement: SetValueMovement) {
        when (val previousCellState = movement.previousCellState) {
            is PreviousCellState.SingleValue -> {
                updateCell(
                    movement.copy(
                        previousCellState = PreviousCellState.SingleValue(movement.newValue),
                        newValue = previousCellState.value
                    )
                )
            }
            is PreviousCellState.Possibilities -> {
                updateCell(
                    SetPossibilitiesMovement(
                        row = movement.row,
                        column = movement.column,
                        previousCellValue = movement.newValue,
                        newPossibilities = previousCellState.possibilities,
                        shouldAddPossibilities = true
                    )
                )
            }
        }
    }

    private fun undoSetPossibilitiesMovement(movement: SetPossibilitiesMovement) {
        if (movement.previousCellValue != null) {
            updateCell(
                SetValueMovement(
                    row = movement.row,
                    column = movement.column,
                    previousCellState = PreviousCellState.Possibilities(movement.newPossibilities),
                    newValue = movement.previousCellValue
                )
            )
        } else {
            updateCell(
                movement.copy(
                    shouldAddPossibilities = !movement.shouldAddPossibilities,
                )
            )
        }
    }

    fun clear() {
        for (row in 0 until BOARD_SIDE) {
            for (col in 0 until BOARD_SIDE) {
                if (!board[row, col].isFixed) {
                    board[row, col] = SudokuCellValue.EMPTY
                }
            }
        }
        init()
        movementsDone.clear()
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
