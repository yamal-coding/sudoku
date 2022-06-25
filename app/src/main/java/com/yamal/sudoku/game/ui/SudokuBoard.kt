package com.yamal.sudoku.game.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yamal.sudoku.commons.ui.theme.SudokuTheme
import com.yamal.sudoku.game.domain.ReadOnlyBoard
import com.yamal.sudoku.model.SudokuCellValue
import com.yamal.sudoku.commons.ui.utils.`if`
import com.yamal.sudoku.game.domain.BOARD_SIDE
import com.yamal.sudoku.game.domain.Board
import com.yamal.sudoku.game.domain.QUADRANTS_PER_SIDE
import com.yamal.sudoku.game.status.data.toSudokuCell
import com.yamal.sudoku.model.SudokuCell

@Composable
fun SudokuBoard(
    modifier: Modifier,
    board: ReadOnlyBoard,
    selectedRow: Int?,
    selectedColumn: Int?,
    onCellSelected: (row: Int, column: Int) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        StrongHorizontalDivider()
        for (row in 0 until BOARD_SIDE) {
            Row(
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                StrongVerticalDivider()
                for (column in 0 until BOARD_SIDE) {
                    val cell = board[row, column]
                    SudokuCell(
                        modifier = Modifier.weight(1F),
                        sudokuCellValue = cell.value,
                        onSelected = {
                            onCellSelected(row, column)
                        },
                        isFixed = cell.isFixed,
                        isSelected = row == selectedRow && column == selectedColumn,
                    )
                    if ((column + 1) % QUADRANTS_PER_SIDE == 0) {
                        StrongVerticalDivider()
                    } else {
                        LightVerticalDivider()
                    }
                }
            }
            if ((row + 1) % QUADRANTS_PER_SIDE == 0) {
                StrongHorizontalDivider()
            } else {
                LightHorizontalDivider()
            }
        }
    }
}

@Composable
private fun SudokuCell(
    modifier: Modifier = Modifier,
    sudokuCellValue: SudokuCellValue,
    onSelected: () -> Unit,
    isFixed: Boolean,
    isSelected: Boolean,
) {
    Box(
        modifier = modifier
            .aspectRatio(1F)
            .background(
                when {
                    isFixed -> SudokuTheme.colors.fixedCellBackground
                    isSelected -> SudokuTheme.colors.selectedCellBackground
                    else -> SudokuTheme.colors.cellBackground
                }
            )
            .`if`(!isFixed) {
                clickable { onSelected() }
            }
            .padding(1.dp),
        contentAlignment = Alignment.Center
    ) {
        getSudokuCellIconOrNullIfEmpty(sudokuCellValue)?.let {
            Icon(imageVector = it, contentDescription = null)
        }
    }
}

@Suppress("MagicNumber")
@Preview
@Composable
fun SudokuCellPreview() {
    SudokuTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            SudokuBoard(
                modifier = Modifier.padding(8.dp),
                board = Board(
                    cells = notFixedCells(
                        5, 3, 4, 6, 7, 8, 9, 1, 2,
                        6, 7, 2, 1, 9, 5, 3, 4, 8,
                        1, 0, 8, 3, 4, 2, 5, 6, 7,
                        8, 5, 9, 7, 6, 1, 4, 2, 3,
                        4, 2, 6, 8, 5, 3, 7, 9, 1,
                        7, 1, 3, 9, 2, 4, 8, 5, 6,
                        9, 6, 1, 5, 0, 7, 0, 8, 4,
                        2, 8, 7, 4, 1, 9, 6, 3, 5,
                        3, 4, 5, 0, 8, 6, 1,
                    ).also {
                        it.add(
                            SudokuCell(
                                SudokuCellValue.ONE,
                                isFixed = true
                            )
                        )
                        it.add(
                            SudokuCell(
                                SudokuCellValue.EMPTY,
                                isFixed = false
                            )
                        )
                    }
                ),
                selectedRow = null,
                selectedColumn = null,
                onCellSelected = { _, _ -> }
            )

            NumberPad(
                modifier = Modifier.padding(8.dp),
                onValueSelected = {}
            )
        }
    }
}

private fun notFixedCells(vararg items: Int): MutableList<SudokuCell> =
    items.map { SudokuCell(it.toSudokuCell(), isFixed = false) }.toMutableList()