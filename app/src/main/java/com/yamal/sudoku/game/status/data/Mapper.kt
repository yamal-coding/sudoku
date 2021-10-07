package com.yamal.sudoku.game.status.data

import com.yamal.sudoku.model.Board
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.model.ReadOnlyBoard
import com.yamal.sudoku.model.SudokuCell
import com.yamal.sudoku.model.SudokuCellValue
import com.yamal.sudoku.storage.model.BoardDO
import com.yamal.sudoku.storage.model.DifficultyDO
import com.yamal.sudoku.storage.model.SudokuCellDO
import java.lang.IllegalStateException

fun SudokuCell.toDO(): SudokuCellDO {
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

fun SudokuCellDO.toDomain(): SudokuCell =
    SudokuCell(value.toSudokuCell(), isFixed)

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
    val list = mutableListOf<List<SudokuCellDO>>()

    getAllCells().forEach { row ->
        list.add(row.map { it.toDO() })
    }

    return BoardDO(
        cells = list,
        difficulty = difficulty.toDO()
    )
}

private fun Difficulty.toDO(): String? =
    when (this) {
        Difficulty.EASY -> DifficultyDO.EASY
        Difficulty.MEDIUM -> DifficultyDO.MEDIUM
        Difficulty.HARD -> DifficultyDO.HARD
        Difficulty.UNKNOWN -> null
    }

fun BoardDO.toDomain(): Board {
    val list = mutableListOf<MutableList<SudokuCell>>()

    cells.forEach { row ->
        list.add(row.map { it.toDomain() }.toMutableList())
    }

    return Board(
        cells = list,
        difficulty = difficultyDOtoDomain(difficulty)
    )
}

private fun difficultyDOtoDomain(difficultyDO: String?): Difficulty =
    when (difficultyDO) {
        DifficultyDO.EASY -> Difficulty.EASY
        DifficultyDO.MEDIUM -> Difficulty.MEDIUM
        DifficultyDO.HARD -> Difficulty.HARD
        else -> Difficulty.UNKNOWN
    }
