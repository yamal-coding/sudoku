package com.yamal.sudoku.game.status.data

import com.yamal.sudoku.game.domain.BOARD_SIDE
import com.yamal.sudoku.game.domain.Board
import com.yamal.sudoku.game.domain.ReadOnlyBoard
import com.yamal.sudoku.model.SudokuCell
import com.yamal.sudoku.model.SudokuCellValue
import com.yamal.sudoku.game.status.data.storage.model.BoardDO
import com.yamal.sudoku.game.status.data.storage.model.DifficultyDO
import com.yamal.sudoku.game.status.data.storage.model.SudokuCellDO
import com.yamal.sudoku.model.Difficulty
import java.lang.IllegalStateException

@Suppress("MagicNumber")
fun Int.toSudokuCell(): SudokuCellValue =
    when (this) {
        0 -> SudokuCellValue.EMPTY
        1 -> SudokuCellValue.ONE
        2 -> SudokuCellValue.TWO
        3 -> SudokuCellValue.THREE
        4 -> SudokuCellValue.FOUR
        5 -> SudokuCellValue.FIVE
        6 -> SudokuCellValue.SIX
        7 -> SudokuCellValue.SEVEN
        8 -> SudokuCellValue.EIGHT
        9 -> SudokuCellValue.NINE
        else -> throw IllegalStateException("Can't parse sudoku with cell value $this")
    }

fun ReadOnlyBoard.toDO(): BoardDO {
    val list = mutableListOf<SudokuCellDO>()

    for (row in 0 until BOARD_SIDE) {
        for (col in 0 until BOARD_SIDE) {
            list.add(this[row, col].toDO())
        }
    }

    return BoardDO(
        cells = list,
        difficulty = difficulty.toDO()
    )
}

@Suppress("MagicNumber")
private fun SudokuCell.toDO(): SudokuCellDO {
    val value = when (this.value) {
        SudokuCellValue.EMPTY -> 0
        SudokuCellValue.ONE -> 1
        SudokuCellValue.TWO -> 2
        SudokuCellValue.THREE -> 3
        SudokuCellValue.FOUR -> 4
        SudokuCellValue.FIVE -> 5
        SudokuCellValue.SIX -> 6
        SudokuCellValue.SEVEN -> 7
        SudokuCellValue.EIGHT -> 8
        SudokuCellValue.NINE -> 9
    }
    return SudokuCellDO(value, isFixed)
}

fun BoardDO.toDomain(): Board? {
    val list = mutableListOf<SudokuCell>()

    cells.forEach { cell ->
        list.add(cell.toDomain())
    }

    return Board(
        cells = list,
        difficulty = difficultyFromString(difficulty) ?: return null
    )
}

private fun Difficulty.toDO(): String =
    when (this) {
        Difficulty.EASY -> DifficultyDO.EASY
        Difficulty.MEDIUM -> DifficultyDO.MEDIUM
        Difficulty.HARD -> DifficultyDO.HARD
    }

private fun difficultyFromString(value: String): Difficulty? =
    when (value) {
        DifficultyDO.EASY -> Difficulty.EASY
        DifficultyDO.MEDIUM -> Difficulty.MEDIUM
        DifficultyDO.HARD -> Difficulty.HARD
        else -> null
    }

private fun SudokuCellDO.toDomain(): SudokuCell =
    SudokuCell(value.toSudokuCell(), isFixed)

