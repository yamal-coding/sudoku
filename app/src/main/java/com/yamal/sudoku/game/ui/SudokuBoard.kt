package com.yamal.sudoku.game.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yamal.sudoku.BuildConfig
import com.yamal.sudoku.R
import com.yamal.sudoku.commons.ui.theme.SudokuTheme
import com.yamal.sudoku.game.domain.ReadOnlyBoard
import com.yamal.sudoku.model.SudokuCell as Cell
import com.yamal.sudoku.commons.ui.utils.`if`
import com.yamal.sudoku.game.domain.BOARD_SIDE
import com.yamal.sudoku.game.domain.QUADRANTS_PER_SIDE
import com.yamal.sudoku.model.SudokuCellValue

@Composable
fun SudokuBoard(
    modifier: Modifier,
    board: ReadOnlyBoard,
    selectedRow: Int?,
    selectedColumn: Int?,
    gameHasFinished: Boolean,
    onCellSelected: (row: Int, column: Int) -> Unit,
) {
    AnimatedVisibility(
        visible = !gameHasFinished,
        exit = fadeOut(
            animationSpec = tween(
                durationMillis = GameAnimationConstants.FadeOutTransitionDuration,
            )
        )
    ) {
        SudokuBoardImpl(
            modifier = modifier,
            board = board,
            selectedRow = selectedRow,
            selectedColumn = selectedColumn,
            gameHasFinished = gameHasFinished,
            onCellSelected = onCellSelected
        )
    }
}

@Composable
private fun SudokuBoardImpl(
    modifier: Modifier,
    board: ReadOnlyBoard,
    selectedRow: Int?,
    selectedColumn: Int?,
    gameHasFinished: Boolean,
    onCellSelected: (row: Int, column: Int) -> Unit,
) {
    Column(
        modifier = modifier.aspectRatio(1F),
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
                        cell = cell,
                        onSelected = {
                            onCellSelected(row, column)
                        },
                        isSelected = row == selectedRow && column == selectedColumn,
                        gameHasFinished = gameHasFinished,
                        testTag = if (BuildConfig.DEBUG) {
                            GameTestTags.cell(row = row, column = column)
                        } else {
                            null
                        },
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
    cell: Cell,
    onSelected: () -> Unit,
    isSelected: Boolean,
    gameHasFinished: Boolean,
    testTag: String?,
) {
    val onClickLabel = if (cell.value != SudokuCellValue.EMPTY) {
        stringResource(id = R.string.empty_cell_accessibility_label)
    } else {
        ""
    }

    val baseCellModifier = modifier
        .aspectRatio(1F)
        .background(
            when {
                gameHasFinished -> SudokuTheme.colors.gameFinishedCellBackground
                cell.isFixed -> SudokuTheme.colors.fixedCellBackground
                isSelected -> SudokuTheme.colors.selectedCellBackground
                else -> SudokuTheme.colors.cellBackground
            }
        )
        .`if`(!cell.isFixed) {
            clickable(onClickLabel = onClickLabel) { onSelected() }
        }
        .padding(1.dp)

    val cellModifier = testTag?.let {
        baseCellModifier.then(Modifier.testTag(it))
    } ?: baseCellModifier

    Box(
        modifier = cellModifier,
        contentAlignment = Alignment.Center
    ) {
        when {
            cell.value != SudokuCellValue.EMPTY -> CellValueIcon(
                modifier = Modifier.fillMaxSize(fraction = 0.7F),
                value = cell.value
            )
            cell.possibilities?.isNotEmpty() == true -> CellPossibilities(cell.possibilities)
        }
    }
}

@Composable
private fun CellValueIcon(
    modifier: Modifier = Modifier,
    value: SudokuCellValue
) {
    getSudokuCellIconOrNullIfEmpty(value)?.let {
        Icon(
            modifier = modifier,
            painter = painterResource(id = it),
            contentDescription = value.toString(),
            tint = SudokuTheme.colors.cellText
        )
    }
}

@Composable
private fun CellPossibilities(
    possibilities: Set<SudokuCellValue>
) {
    @Composable
    fun Possibilities(vararg values: SudokuCellValue) {
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            values.forEach { value ->
                CellValueIcon(
                    modifier = Modifier
                        .weight(1F)
                        .alpha(if (possibilities.contains(value)) 1F else 0F),
                    value = value,
                )
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Possibilities(SudokuCellValue.ONE, SudokuCellValue.TWO,SudokuCellValue.THREE)
        Possibilities(SudokuCellValue.FOUR, SudokuCellValue.FIVE, SudokuCellValue.SIX)
        Possibilities(SudokuCellValue.SEVEN, SudokuCellValue.EIGHT, SudokuCellValue.NINE)
    }
}
