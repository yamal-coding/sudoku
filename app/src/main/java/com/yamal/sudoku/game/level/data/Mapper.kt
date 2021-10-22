package com.yamal.sudoku.game.level.data

import com.yamal.sudoku.game.status.data.toSudokuCell
import com.yamal.sudoku.model.Board
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.model.SudokuCell
import com.yamal.sudoku.model.SudokuCellValue

fun rawLevelToBoard(rawLevel: String, difficulty: Difficulty): Board? {
    val cells = mutableListOf<MutableList<SudokuCell>>(
        mutableListOf(), mutableListOf(), mutableListOf(),
        mutableListOf(), mutableListOf(), mutableListOf(),
        mutableListOf(), mutableListOf(), mutableListOf(),
    )

    if (rawLevel.length != 81) {
        return null
    }

    return try {
        rawLevel.forEachIndexed { index, c ->
            val cellValue = c.toString().toInt().toSudokuCell()
            val cell = SudokuCell(value = cellValue, isFixed = cellValue != SudokuCellValue.EMPTY)
            cells[index / 9].add(cell)
        }
        Board(cells = cells, difficulty = difficulty)
    } catch (e: NumberFormatException) {
        null
    }
}
