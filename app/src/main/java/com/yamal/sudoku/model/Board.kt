package com.yamal.sudoku.model

class Board(private val cells: List<MutableList<SudokuCell>>) {

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

    var selectedX: Int = 0
        private set
    var selectedY: Int = 0
        private set

    val selectedCell: SudokuCell
        get() = cells[selectedX][selectedY]

    operator fun get(x: Int, y: Int): SudokuCell =
        cells[x][y]

    fun selectCell(x: Int, y: Int) {
        selectedX = x
        selectedY = y
    }

    fun setSelectedCell(value: SudokuCellValue) {
        updateCurrentStatus(value)
        cells[selectedX][selectedY].value = value
    }

    fun fixSelectedCell(value: SudokuCellValue) {
        updateCurrentStatus(value)

        cells[selectedX][selectedY].isFixed = value != SudokuCellValue.EMPTY
        cells[selectedX][selectedY].value = value
    }

    private fun updateCurrentStatus(value: SudokuCellValue) {
        if (cells[selectedX][selectedY].value != SudokuCellValue.EMPTY) {
            val intValue = cells[selectedX][selectedY].value.intValue
            rows[selectedX][intValue] = rows[selectedX][intValue]!! - 1
            columns[selectedY][intValue] = columns[selectedY][intValue]!! - 1
            quadrants[quadrantByRowAndColumn(selectedX, selectedY)][intValue] =
                quadrants[quadrantByRowAndColumn(selectedX, selectedY)][intValue]!! - 1
        }

        if (value != SudokuCellValue.EMPTY) {
            rows[selectedX][value.intValue] = rows[selectedX][value.intValue]!! + 1
            columns[selectedY][value.intValue] = columns[selectedY][value.intValue]!! + 1
            quadrants[quadrantByRowAndColumn(selectedX, selectedY)][value.intValue] =
                quadrants[quadrantByRowAndColumn(selectedX, selectedY)][value.intValue]!! + 1
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