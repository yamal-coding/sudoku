package com.yamal.sudoku.model

import androidx.annotation.VisibleForTesting
import com.yamal.sudoku.game.status.data.toSudokuCell

interface ReadOnlyBoard {
    val difficulty: Difficulty?
    fun getSelectedX(): Int
    fun getSelectedY(): Int
    operator fun get(x: Int, y: Int): SudokuCell
    fun getAllCells(): List<List<SudokuCell>>
}

class Board(
    private val cells: List<MutableList<SudokuCell>>,
    override val difficulty: Difficulty?
) : ReadOnlyBoard {

    private val rows = mapOfCells()
    private val columns = mapOfCells()
    private val quadrants = mapOfCells()

    init {
        for (y in 0..8) {
            for (x in 0..8) {
                val cell = cells[y][x]
                if (cell.value != SudokuCellValue.EMPTY) {
                    val intValue = cell.value.intValue
                    rows[y][intValue] = rows[y][intValue]!! + 1
                    columns[x][intValue] = columns[x][intValue]!! + 1
                    quadrants[quadrantByRowAndColumn(y, x)][intValue] =
                        quadrants[quadrantByRowAndColumn(y, x)][intValue]!! + 1
                }
            }
        }
    }

    private var _selectedX: Int = 0
    override fun getSelectedX(): Int = _selectedX

    private var _selectedY: Int = 0
    override fun getSelectedY(): Int = _selectedY

    val selectedCell: SudokuCell
        get() = cells[_selectedX][_selectedY]

    override fun get(x: Int, y: Int): SudokuCell =
        cells[x][y]

    override fun getAllCells(): List<List<SudokuCell>> = cells

    fun selectCell(x: Int, y: Int) {
        _selectedX = x
        _selectedY = y
    }

    fun setSelectedCell(value: SudokuCellValue) {
        updateCurrentStatus(value)
        cells[_selectedX][_selectedY].value = value
    }

    fun fixSelectedCell(value: SudokuCellValue) {
        updateCurrentStatus(value)

        cells[_selectedX][_selectedY].isFixed = value != SudokuCellValue.EMPTY
        cells[_selectedX][_selectedY].value = value
    }

    private fun updateCurrentStatus(value: SudokuCellValue) {
        if (cells[_selectedX][_selectedY].value != SudokuCellValue.EMPTY) {
            val intValue = cells[_selectedX][_selectedY].value.intValue
            rows[_selectedX][intValue] = rows[_selectedX][intValue]!! - 1
            columns[_selectedY][intValue] = columns[_selectedY][intValue]!! - 1
            quadrants[quadrantByRowAndColumn(_selectedX, _selectedY)][intValue] =
                quadrants[quadrantByRowAndColumn(_selectedX, _selectedY)][intValue]!! - 1
        }

        if (value != SudokuCellValue.EMPTY) {
            rows[_selectedX][value.intValue] = rows[_selectedX][value.intValue]!! + 1
            columns[_selectedY][value.intValue] = columns[_selectedY][value.intValue]!! + 1
            quadrants[quadrantByRowAndColumn(_selectedX, _selectedY)][value.intValue] =
                quadrants[quadrantByRowAndColumn(_selectedX, _selectedY)][value.intValue]!! + 1
        }
    }

    fun isSolved(): Boolean {
        val rowsOk = rows.all { rowMap ->
            rowMap.all { cellEntry -> cellEntry.value == 1 }
        }

        val columnsOk = columns.all { columnMap ->
            columnMap.all { cellEntry -> cellEntry.value == 1 }
        }

        val quadrantsOk = columns.all { quadrantMap ->
            quadrantMap.all { cellEntry -> cellEntry.value == 1 }
        }

        return rowsOk && columnsOk && quadrantsOk
    }

    private fun mapOfCells(): List<MutableMap<Int, Int>> {
        val list = mutableListOf<MutableMap<Int, Int>>()

        for (i in 1..9) {
            val map = mutableMapOf<Int, Int>()
            for (j in 1..9) {
                map[j] = 0
            }
            list.add(map)
        }

        return list
    }

    private fun quadrantByRowAndColumn(row: Int, column: Int): Int =
        when(row) {
            in 0..2 -> {
                when (column) {
                    in 0..2 -> 0
                    in 3..5 -> 1
                    in 6..8 -> 2
                    else -> throw IllegalArgumentException("Row $row is not in range [0, 8]")
                }
            }
            in 3..5 -> {
                when (column) {
                    in 0..2 -> 3
                    in 3..5 -> 4
                    in 6..8 -> 5
                    else -> throw IllegalArgumentException("Row $row is not in range [0, 8]")
                }
            }
            in 6..8 -> {
                when (column) {
                    in 0..2 -> 6
                    in 3..5 -> 7
                    in 6..8 -> 8
                    else -> throw IllegalArgumentException("Row $row is not in range [0, 8]")
                }
            }
            else -> throw IllegalArgumentException("Column $column is not in range [0, 8]")
        }

    override fun equals(other: Any?): Boolean =
        other != null
                && other is ReadOnlyBoard
                && hasSameContent(other)
                && difficulty == other.difficulty

    private fun hasSameContent(other: ReadOnlyBoard): Boolean {
        for (x in 0..8) {
            for (y in 0..8) {
                if (cells[x][y] != other[x, y]) {
                    return false
                }
            }
        }
        return true
    }

    override fun hashCode(): Int {
        var result = cells.hashCode()
        result = 31 * result + difficulty.hashCode()
        result = 31 * result + rows.hashCode()
        result = 31 * result + columns.hashCode()
        result = 31 * result + quadrants.hashCode()
        result = 31 * result + _selectedX
        result = 31 * result + _selectedY
        result = 31 * result + selectedCell.hashCode()
        return result
    }

    companion object {
        fun empty(): Board =
            Board(
                cells = listOf(
                    emptyRow(), emptyRow(), emptyRow(),
                    emptyRow(), emptyRow(), emptyRow(),
                    emptyRow(), emptyRow(), emptyRow()
                ),
                difficulty = null
            )

        private fun emptyRow(): MutableList<SudokuCell> =
            mutableListOf(
                emptyCell(), emptyCell(), emptyCell(),
                emptyCell(), emptyCell(), emptyCell(),
                emptyCell(), emptyCell(), emptyCell()
            )

        private fun emptyCell(): SudokuCell =
            SudokuCell(value = SudokuCellValue.EMPTY, isFixed = false)

        // TODO remove from here when DataSource is implemented
        fun almostDone(): Board =
            Board(
                cells = listOf(
                    rowOf(5, 3, 4, 6, 7, 8, 9, 1, 2),
                    rowOf(6, 7, 2, 1, 9, 5, 3, 4, 8),
                    rowOf(1, 9, 8, 3, 4, 2, 5, 6, 7),
                    rowOf(8, 5, 9, 7, 6, 1, 4, 2, 3),
                    rowOf(4, 2, 6, 8, 5, 3, 7, 9, 1),
                    rowOf(7, 1, 3, 9, 2, 4, 8, 5, 6),
                    rowOf(9, 6, 1, 5, 3, 7, 2, 8, 4),
                    rowOf(2, 8, 7, 4, 1, 9, 6, 3, 5),
                    rowOf(3, 4, 5, 2, 8, 6, 1, 7).also {
                        it.add(SudokuCell(SudokuCellValue.EMPTY, isFixed = false))
                    }
                ),
                difficulty = null
            )

        // TODO remove from here
        @VisibleForTesting
        fun rowOf(vararg items: Int): MutableList<SudokuCell> =
            items.map { SudokuCell(it.toSudokuCell(), isFixed = false) }.toMutableList()
    }
}