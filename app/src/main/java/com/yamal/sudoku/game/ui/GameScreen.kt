package com.yamal.sudoku.game.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yamal.sudoku.R
import com.yamal.sudoku.commons.ui.BackButton
import com.yamal.sudoku.commons.ui.Dialog
import com.yamal.sudoku.commons.ui.SudokuTopBar
import com.yamal.sudoku.commons.ui.theme.SudokuTheme
import com.yamal.sudoku.game.domain.Board
import com.yamal.sudoku.game.status.data.toSudokuCell
import com.yamal.sudoku.game.viewmodel.SudokuViewModel
import com.yamal.sudoku.game.viewmodel.SudokuViewState
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.model.SudokuCell
import com.yamal.sudoku.model.SudokuCellValue

@Composable
fun ExistingGameScreen(
    viewModel: SudokuViewModel,
    onBackClicked: () -> Unit,
) {
    GameScreen(
        viewModel = viewModel,
        onInit = {
            viewModel.initExistingGame()
        },
        onBackClicked = onBackClicked,
    )
}

@Composable
fun NewGameScreen(
    difficulty: Difficulty,
    viewModel: SudokuViewModel,
    onBackClicked: () -> Unit,
) {
    GameScreen(
        viewModel = viewModel,
        onInit = {
            viewModel.initNewGame(difficulty)
        },
        onBackClicked = onBackClicked,
    )
}

@Composable
private fun GameScreen(
    viewModel: SudokuViewModel,
    onInit: () -> Unit,
    onBackClicked: () -> Unit,
) {
    Scaffold(
        topBar = {
            SudokuTopBar(
                navigationIcon = {
                    BackButton(
                        onBackClicked = onBackClicked
                    )
                }
            )
        }
    ) {
        LaunchedEffect(viewModel) {
            onInit()
        }

        val state by viewModel.state.collectAsState(initial = SudokuViewState.Idle)
        when (state) {
            is SudokuViewState.Idle -> {}
            is SudokuViewState.Loading -> {}
            is SudokuViewState.NewBoardNotFound -> { /* TODO */ }
            is SudokuViewState.SavedGameNotFound -> { /* TODO */ }
            is SudokuViewState.GameFinished -> { /* TODO */ }
            is SudokuViewState.UpdatedBoard -> {
                val shouldShowClearBoardConfirmationDialog by
                    viewModel.shouldShowClearBoardConfirmationDialog.collectAsState(initial = false)
                val isPossibilitiesModeEnabled by
                    viewModel.isPossibilitiesModeEnabled.collectAsState(initial = false)

                UpdatedBoard(
                    updatedBoard = state as SudokuViewState.UpdatedBoard,
                    onCellSelected = viewModel::onCellSelected,
                    onValueSelected = viewModel::selectNumber,
                    onUndo = viewModel::undo,
                    shouldShowClearBoardConfirmationDialog = shouldShowClearBoardConfirmationDialog,
                    onShowClearBoardConfirmationDialog = viewModel::showClearBoardConfirmationDialog,
                    onHideClearBoardConfirmationDialog = viewModel::hideClearBoardConfirmationDialog,
                    onClear = viewModel::clear,
                    isPossibilitiesModeEnabled = isPossibilitiesModeEnabled,
                    onEnablePossibilitiesMode = viewModel::onEnablePossibilitiesMode,
                    onDisablePossibilitiesMode = viewModel::onDisablePossibilitiesMode,
                )
            }
        }
    }
}

@Composable
private fun UpdatedBoard(
    updatedBoard: SudokuViewState.UpdatedBoard,
    onCellSelected: (row: Int, column: Int) -> Unit,
    onValueSelected: (SudokuCellValue) -> Unit,
    onUndo: () -> Unit,
    shouldShowClearBoardConfirmationDialog: Boolean,
    onShowClearBoardConfirmationDialog: () -> Unit,
    onHideClearBoardConfirmationDialog: () -> Unit,
    onClear: () -> Unit,
    isPossibilitiesModeEnabled: Boolean,
    onEnablePossibilitiesMode: () -> Unit,
    onDisablePossibilitiesMode: () -> Unit,
) {
    if (shouldShowClearBoardConfirmationDialog) {
        ClearBoardConfirmationDialog(
            onHideClearBoardConfirmationDialog = onHideClearBoardConfirmationDialog,
            onClear = onClear
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        DifficultyLabel(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 8.dp),
            difficulty = updatedBoard.board.difficulty
        )
        SudokuBoard(
            modifier = Modifier.padding(8.dp),
            board = updatedBoard.board,
            selectedRow = updatedBoard.selectedRow,
            selectedColumn = updatedBoard.selectedColumn,
            onCellSelected = onCellSelected,
        )
        MovementsPad(
            canUndo = updatedBoard.canUndo,
            onUndo = onUndo,
            onClear = onShowClearBoardConfirmationDialog,
            isPossibilitiesModeEnabled = isPossibilitiesModeEnabled,
            onEnablePossibilitiesMode = onEnablePossibilitiesMode,
            onDisablePossibilitiesMode = onDisablePossibilitiesMode,
        )
        NumberPad(
            modifier = Modifier.padding(8.dp),
            onValueSelected = onValueSelected
        )
    }
}

@Composable
private fun ClearBoardConfirmationDialog(
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
private fun DifficultyLabel(
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
private fun MovementsPad(
    canUndo: Boolean,
    onUndo: () -> Unit,
    onClear: () -> Unit,
    isPossibilitiesModeEnabled: Boolean,
    onEnablePossibilitiesMode: () -> Unit,
    onDisablePossibilitiesMode: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onClear,
        ) {
            Text(text = stringResource(id = R.string.clear_button))
        }
        Button(
            onClick = onUndo,
            enabled = canUndo
        ) {
            Text(text = stringResource(id = R.string.undo_button))
        }
        Button(
            onClick = if (isPossibilitiesModeEnabled) {
                onDisablePossibilitiesMode
            } else {
                onEnablePossibilitiesMode
            }
        ) {
            Text(text = stringResource(
                id = if (isPossibilitiesModeEnabled) {
                    R.string.annotate_button_on
                } else {
                    R.string.annotate_button_off
                }
            ))
        }
    }
}

@Suppress("MagicNumber")
@Preview
@Composable
private fun UpdatedBoardPreview() {
    SudokuTheme {
        val state = SudokuViewState.UpdatedBoard(
            board = Board(
                difficulty = Difficulty.EASY,
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
                            isFixed = true,
                            possibilities = null,
                        )
                    )
                    it.add(
                        SudokuCell(
                            SudokuCellValue.EMPTY,
                            isFixed = false,
                            possibilities = setOf(
                                SudokuCellValue.ONE,
                                SudokuCellValue.TWO,
                                SudokuCellValue.FIVE,
                                SudokuCellValue.SEVEN,
                                SudokuCellValue.NINE
                            ),
                        )
                    )
                }
            ),
            selectedRow = null,
            selectedColumn = null,
            canUndo = true,
        )

        UpdatedBoard(
            updatedBoard = state,
            onCellSelected = { _, _ -> },
            onValueSelected = {},
            onUndo = {},
            shouldShowClearBoardConfirmationDialog = false,
            onShowClearBoardConfirmationDialog = {},
            onHideClearBoardConfirmationDialog = {},
            onClear = {},
            isPossibilitiesModeEnabled = false,
            onEnablePossibilitiesMode = {},
            onDisablePossibilitiesMode = {},
        )
    }
}

private fun notFixedCells(vararg items: Int): MutableList<SudokuCell> =
    items.map { SudokuCell(it.toSudokuCell(), isFixed = false) }.toMutableList()
