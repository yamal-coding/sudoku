package com.yamal.sudoku.game.domain

import com.yamal.sudoku.commons.utils.get
import com.yamal.sudoku.commons.utils.set
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.model.SudokuCell
import com.yamal.sudoku.model.SudokuCellValue

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
        cells[row, col] = cells[row, col].copy(
            value = value,
            possibilities = null
        )
    }

    fun addPossibilities(row: Int, col: Int, possibilities: Set<SudokuCellValue>) {
        modifyPossibilities(
            row = row,
            col = col,
            possibilities = possibilities,
            operator = Set<SudokuCellValue>::plus
        )
    }

    fun removePossibilities(row: Int, col: Int, possibilities: Set<SudokuCellValue>) {
        modifyPossibilities(
            row = row,
            col = col,
            possibilities = possibilities,
            operator = Set<SudokuCellValue>::minus
        )
    }

    private fun modifyPossibilities(
        row: Int,
        col: Int,
        possibilities: Set<SudokuCellValue>,
        operator: Set<SudokuCellValue>.(elements: Iterable<SudokuCellValue>) -> Set<SudokuCellValue>
    ) {
        val currentPossibilities = cells[row, col].possibilities ?: setOf()
        val newPossibilities = currentPossibilities.operator(possibilities)
        cells[row, col] = cells[row, col].copy(possibilities = newPossibilities.toSet())
    }

    fun copy(): Board =
        Board(
            mutableListOf<SudokuCell>().also { it.addAll(cells) },
            difficulty = difficulty
        )
}