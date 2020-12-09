package com.yamal.sudoku.model

interface OnlyReadBoard {
    fun getSelectedX(): Int
    fun getSelectedY(): Int
    operator fun get(x: Int, y: Int): SudokuCell
}

class Board(private val cells: List<MutableList<SudokuCell>>) : OnlyReadBoard {

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

    private fun mapOfCells(): List<MutableMap<Int, Int>> = listOf(
        mutableMapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0),
        mutableMapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0),
        mutableMapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0),
        mutableMapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0),
        mutableMapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0),
        mutableMapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0),
        mutableMapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0),
        mutableMapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0),
        mutableMapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
    )

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

    companion object {
        fun empty(): Board =
            Board(
                listOf(
                    emptyRow(), emptyRow(), emptyRow(),
                    emptyRow(), emptyRow(), emptyRow(),
                    emptyRow(), emptyRow(), emptyRow()
                )
            )

        private fun emptyRow(): MutableList<SudokuCell> =
            mutableListOf(
                emptyCell(), emptyCell(), emptyCell(),
                emptyCell(), emptyCell(), emptyCell(),
                emptyCell(), emptyCell(), emptyCell()
            )

        private fun emptyCell(): SudokuCell =
            SudokuCell(value = SudokuCellValue.EMPTY, isFixed = false)
    }
}