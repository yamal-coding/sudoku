package com.yamal.sudoku.repository

import com.yamal.sudoku.model.Board
import com.yamal.sudoku.model.ReadOnlyBoard
import com.yamal.sudoku.model.SudokuCell
import com.yamal.sudoku.model.SudokuCellValue
import com.yamal.sudoku.storage.model.BoardDO
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

    return BoardDO(list)
}

fun BoardDO.toDomain(): Board {
    val list = mutableListOf<MutableList<SudokuCell>>()

    cells.forEach { row ->
        list.add(row.map { it.toDomain() }.toMutableList())
    }

    return Board(list)
}
