package com.yamal.sudoku.game.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
    board: ReadOnlyBoard
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
                        onSelected = { /*TODO*/ },
                        isFixed = cell.isFixed
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
) {
    Box(
        modifier = modifier
            .aspectRatio(1F)
            .background(
                if (isFixed) {
                    SudokuTheme.colors.fixedCellBackground
                } else {
                    SudokuTheme.colors.cellBackground
                }
            )
            .`if`(!isFixed) {
                clickable { onSelected() }
            }
            .padding(1.dp)
        ,
        contentAlignment = Alignment.Center
    ) {
        getSudokuCellIcon(sudokuCellValue)?.let {
            Icon(imageVector = it, contentDescription = null)
        }
    }
}


// TODO use number image vectors instead of random icons
private fun getSudokuCellIcon(
    value: SudokuCellValue
): ImageVector? =
    when (value) {
        SudokuCellValue.EMPTY -> null
        SudokuCellValue.ONE -> Icons.Default.Info
        SudokuCellValue.TWO -> Icons.Default.Add
        SudokuCellValue.THREE -> Icons.Default.ArrowBack
        SudokuCellValue.FOUR -> Icons.Default.ThumbUp
        SudokuCellValue.FIVE -> Icons.Default.Check
        SudokuCellValue.SIX -> Icons.Default.Call
        SudokuCellValue.SEVEN -> Icons.Default.AccountCircle
        SudokuCellValue.EIGHT -> Icons.Default.Lock
        SudokuCellValue.NINE -> Icons.Default.Email
    }

@Preview
@Composable
fun SudokuCellPreview() {
    SudokuTheme {
        SudokuBoard(modifier = Modifier.padding(8.dp), board = Board(
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
        ))
    }
}

private fun notFixedCells(vararg items: Int): MutableList<SudokuCell> =
    items.map { SudokuCell(it.toSudokuCell(), isFixed = false) }.toMutableList()