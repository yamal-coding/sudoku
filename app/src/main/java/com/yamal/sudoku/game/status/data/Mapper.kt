package com.yamal.sudoku.game.status.data

import com.yamal.sudoku.game.domain.BOARD_SIDE
import com.yamal.sudoku.game.domain.Board
import com.yamal.sudoku.game.domain.ReadOnlyBoard
import com.yamal.sudoku.model.SudokuCell
import com.yamal.sudoku.model.SudokuCellValue
import com.yamal.sudoku.game.status.data.storage.model.BoardDO
import com.yamal.sudoku.game.status.data.storage.model.DifficultyDO
import com.yamal.sudoku.game.status.data.storage.model.LastFinishedGameSummaryDO
import com.yamal.sudoku.game.status.data.storage.model.SudokuCellDO
import com.yamal.sudoku.game.status.domain.LastFinishedGameSummary
import com.yamal.sudoku.model.Difficulty

private const val EMPTY_INT_VALUE = 0
private const val ONE_INT_VALUE = 1
private const val TWO_INT_VALUE = 2
private const val THREE_INT_VALUE = 3
private const val FOUR_INT_VALUE = 4
private const val FIVE_INT_VALUE = 5
private const val SIX_INT_VALUE = 6
private const val SEVEN_INT_VALUE = 7
private const val EIGHT_INT_VALUE = 8
private const val NINE_INT_VALUE = 9

fun Int.toSudokuCell(): SudokuCellValue =
    when (this) {
        EMPTY_INT_VALUE -> SudokuCellValue.EMPTY
        ONE_INT_VALUE -> SudokuCellValue.ONE
        TWO_INT_VALUE -> SudokuCellValue.TWO
        THREE_INT_VALUE -> SudokuCellValue.THREE
        FOUR_INT_VALUE -> SudokuCellValue.FOUR
        FIVE_INT_VALUE -> SudokuCellValue.FIVE
        SIX_INT_VALUE -> SudokuCellValue.SIX
        SEVEN_INT_VALUE -> SudokuCellValue.SEVEN
        EIGHT_INT_VALUE -> SudokuCellValue.EIGHT
        NINE_INT_VALUE -> SudokuCellValue.NINE
        else -> error("Can't parse sudoku with cell value $this")
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

private fun SudokuCell.toDO(): SudokuCellDO {
    return SudokuCellDO(
        value = value.toInt(),
        isFixed = isFixed,
        possibilities = possibilities?.map { it.toInt() },
    )
}

private fun SudokuCellValue.toInt(): Int =
    when (this) {
        SudokuCellValue.EMPTY -> EMPTY_INT_VALUE
        SudokuCellValue.ONE -> ONE_INT_VALUE
        SudokuCellValue.TWO -> TWO_INT_VALUE
        SudokuCellValue.THREE -> THREE_INT_VALUE
        SudokuCellValue.FOUR -> FOUR_INT_VALUE
        SudokuCellValue.FIVE -> FIVE_INT_VALUE
        SudokuCellValue.SIX -> SIX_INT_VALUE
        SudokuCellValue.SEVEN -> SEVEN_INT_VALUE
        SudokuCellValue.EIGHT -> EIGHT_INT_VALUE
        SudokuCellValue.NINE -> NINE_INT_VALUE
    }

fun BoardDO.toDomain(): Board? {
    val list = mutableListOf<SudokuCell>()

    cells.forEach { cell ->
        list.add(cell.toDomain())
    }

    return Board(
        cells = list,
        difficulty = difficultyFromString(difficulty) ?: return null,
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
    SudokuCell(
        value.toSudokuCell(),
        isFixed,
        possibilities = possibilities?.map { it.toSudokuCell() }?.toSet(),
    )

fun LastFinishedGameSummaryDO.toDomain(): LastFinishedGameSummary =
    LastFinishedGameSummary(
        gameId = gameId,
        gameTimeInSeconds = gameTimeInSeconds,
        isNewBestTime = isNewBestTime,
    )

fun LastFinishedGameSummary.toDO(): LastFinishedGameSummaryDO =
    LastFinishedGameSummaryDO(
        gameId = gameId,
        gameTimeInSeconds = gameTimeInSeconds,
        isNewBestTime = isNewBestTime,
    )
