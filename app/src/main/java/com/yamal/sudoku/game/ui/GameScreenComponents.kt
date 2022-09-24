package com.yamal.sudoku.game.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yamal.sudoku.R
import com.yamal.sudoku.commons.ui.Dialog
import com.yamal.sudoku.game.viewmodel.SudokuViewState
import com.yamal.sudoku.model.Difficulty

@Composable
fun Board(
    modifier: Modifier = Modifier,
    updatedBoard: SudokuViewState.UpdatedBoard,
    onCellSelected: (row: Int, column: Int) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        SudokuBoard(
            modifier = Modifier.padding(8.dp),
            board = updatedBoard.board,
            selectedRow = updatedBoard.selectedRow,
            selectedColumn = updatedBoard.selectedColumn,
            onCellSelected = onCellSelected,
        )
    }
}

@Composable
fun ClearBoardConfirmationDialog(
    onHideClearBoardConfirmationDialog: () -> Unit,
    onClear: () -> Unit,
) {
    Dialog(
        title = stringResource(id = R.string.clear_board_confirmation_dialog_title),
        subtitle = stringResource(id = R.string.clear_board_confirmation_dialog_subtitle),
        onDismissRequest = onHideClearBoardConfirmationDialog,
        leftButtonText = stringResource(id = R.string.clear_board_confirmation_dialog_ok_button),
        onLeftButtonClick = onClear,
        rightButtonText = stringResource(id = R.string.clear_board_confirmation_dialog_cancel_button),
        onRightButtonClick = onHideClearBoardConfirmationDialog
    )
}

@Composable
fun DifficultyLabel(
    modifier: Modifier,
    difficulty: Difficulty
) {
    val labelResId = when (difficulty) {
        Difficulty.EASY -> R.string.difficulty_easy
        Difficulty.MEDIUM -> R.string.difficulty_medium
        Difficulty.HARD -> R.string.difficulty_hard
    }

    Text(
        modifier = modifier,
        text = stringResource(id = labelResId)
    )
}

@Composable
fun ClearButton(
    modifier: Modifier = Modifier,
    onClear: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = onClear,
        iconRes = R.drawable.ic_clear,
        textRes = R.string.clear_button
    )
}

@Composable
fun UndoButton(
    modifier: Modifier = Modifier,
    onUndo: () -> Unit,
    canUndo: Boolean
) {
    IconButton(
        modifier = modifier,
        onClick = onUndo,
        iconRes = R.drawable.ic_undo,
        textRes = R.string.undo_button,
        enabled = canUndo
    )
}

@Composable
fun RemoveCellButton(
    modifier: Modifier = Modifier,
    onRemoveCellValue: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = onRemoveCellValue,
        iconRes = R.drawable.ic_remove,
        textRes = R.string.remove_button,
    )
}

@Composable
fun PossibilitiesButton(
    modifier: Modifier = Modifier,
    isPossibilitiesModeEnabled: Boolean,
    onEnablePossibilitiesMode: () -> Unit,
    onDisablePossibilitiesMode: () -> Unit,
) {
    IconButton(
        modifier = modifier,
        onClick = if (isPossibilitiesModeEnabled) {
            onDisablePossibilitiesMode
        } else {
            onEnablePossibilitiesMode
        },
        iconRes = R.drawable.ic_notes,
        textRes = if (isPossibilitiesModeEnabled) {
            R.string.annotate_button_on
        } else {
            R.string.annotate_button_off
        },
    )
}

@Composable
private fun IconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @DrawableRes iconRes: Int,
    @StringRes textRes: Int,
    enabled: Boolean = true,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(painter = painterResource(id = iconRes), contentDescription = null)
            Text(text = stringResource(id = textRes))
        }
    }
}