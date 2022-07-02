package com.yamal.sudoku.game.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yamal.sudoku.commons.ui.theme.SudokuTheme
import com.yamal.sudoku.game.domain.ReadOnlyBoard
import com.yamal.sudoku.model.SudokuCellValue
import com.yamal.sudoku.commons.ui.utils.`if`
import com.yamal.sudoku.game.domain.BOARD_SIDE
import com.yamal.sudoku.game.domain.QUADRANTS_PER_SIDE

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
