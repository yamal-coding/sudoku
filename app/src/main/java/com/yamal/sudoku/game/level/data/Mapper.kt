package com.yamal.sudoku.game.level.data

import com.yamal.sudoku.game.domain.BOARD_SIDE
import com.yamal.sudoku.game.domain.Board
import com.yamal.sudoku.game.status.data.toSudokuCell
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.model.SudokuCell
import com.yamal.sudoku.model.SudokuCellValue

fun rawLevelToBoard(
    rawLevel: String,
    difficulty: Difficulty,
): Board? {
    val cells = mutableListOf<SudokuCell>()

    if (rawLevel.length != BOARD_SIDE * BOARD_SIDE) {
        return null
    }

    return try {
        rawLevel.forEach { c ->
            val cellValue = c.toString().toInt().toSudokuCell()
            val cell = SudokuCell(value = cellValue, isFixed = cellValue != SudokuCellValue.EMPTY)
            cells.add(cell)
        }
        Board(
            cells = cells,
            difficulty = difficulty,
        )
    } catch (e: NumberFormatException) {
        null
    }
}
