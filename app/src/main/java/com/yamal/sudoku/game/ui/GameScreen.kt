package com.yamal.sudoku.game.ui

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import com.yamal.sudoku.commons.ui.theme.SudokuTheme
import com.yamal.sudoku.game.status.data.toSudokuCell
import com.yamal.sudoku.game.viewmodel.SudokuViewModel
import com.yamal.sudoku.game.viewmodel.SudokuViewState
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.model.SudokuCell
import com.yamal.sudoku.model.SudokuCellValue

@Composable
fun ExistingGameScreen(
    viewModel: SudokuViewModel,
) {
    GameScreen(
        viewModel = viewModel,
        onInit = {
            viewModel.initExistingGame()
        },
    )
}

@Composable
fun NewGameScreen(
    difficulty: Difficulty,
    viewModel: SudokuViewModel,
) {
    GameScreen(
        viewModel = viewModel,
        onInit = {
            viewModel.initNewGame(difficulty)
        },
    )
}

@Composable
private fun GameScreen(
    viewModel: SudokuViewModel,
    onInit: () -> Unit,
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
        is SudokuViewState.UpdatedBoard -> {
            val updatedBoard = state as SudokuViewState.UpdatedBoard
            if (updatedBoard.gameHasFinished) {
               GameFinishedScreen()
            } else {
                val shouldShowClearBoardConfirmationDialog by
                    viewModel.shouldShowClearBoardConfirmationDialog.collectAsState(initial = false)
                val isPossibilitiesModeEnabled by
                    viewModel.isPossibilitiesModeEnabled.collectAsState(initial = false)
                UpdatedBoard(
                    updatedBoard = updatedBoard,
                    onCellSelected = viewModel::onCellSelected,
                    onValueSelected = viewModel::setCellValue,
                    onUndo = viewModel::undo,
                    shouldShowClearBoardConfirmationDialog = shouldShowClearBoardConfirmationDialog,
                    onShowClearBoardConfirmationDialog = viewModel::showClearBoardConfirmationDialog,
                    onHideClearBoardConfirmationDialog = viewModel::hideClearBoardConfirmationDialog,
                    onClear = viewModel::clear,
                    isPossibilitiesModeEnabled = isPossibilitiesModeEnabled,
                    onEnablePossibilitiesMode = viewModel::onEnablePossibilitiesMode,
                    onDisablePossibilitiesMode = viewModel::onDisablePossibilitiesMode,
                    onRemoveCellValue = { viewModel.setCellValue(SudokuCellValue.EMPTY) }
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
    onRemoveCellValue: () -> Unit,
) {
    val orientation = LocalConfiguration.current.orientation

    if (shouldShowClearBoardConfirmationDialog) {
        ClearBoardConfirmationDialog(
            onHideClearBoardConfirmationDialog = onHideClearBoardConfirmationDialog,
            onClear = onClear
        )
    }

    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        PortraitUpdatedBoard(
            updatedBoard = updatedBoard,
            onCellSelected = onCellSelected,
            onValueSelected = onValueSelected,
            onUndo = onUndo,
            onShowClearBoardConfirmationDialog = onShowClearBoardConfirmationDialog,
            isPossibilitiesModeEnabled = isPossibilitiesModeEnabled,
            onEnablePossibilitiesMode = onEnablePossibilitiesMode,
            onDisablePossibilitiesMode = onDisablePossibilitiesMode,
            onRemoveCellValue = onRemoveCellValue,
        )
    } else {
        LandscapeUpdatedBoard(
            updatedBoard = updatedBoard,
            onCellSelected = onCellSelected,
            onValueSelected = onValueSelected,
            onUndo = onUndo,
            onShowClearBoardConfirmationDialog = onShowClearBoardConfirmationDialog,
            isPossibilitiesModeEnabled = isPossibilitiesModeEnabled,
            onEnablePossibilitiesMode = onEnablePossibilitiesMode,
            onDisablePossibilitiesMode = onDisablePossibilitiesMode,
            onRemoveCellValue = onRemoveCellValue,
        )
    }
}

@Suppress("MagicNumber")
@Preview
@Composable
private fun UpdatedBoardPreview() {
    SudokuTheme {
        val state = SudokuViewState.UpdatedBoard(
            board = com.yamal.sudoku.game.domain.Board(
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
            gameHasFinished = false,
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
            onRemoveCellValue = {},
        )
    }
}

private fun notFixedCells(vararg items: Int): MutableList<SudokuCell> =
    items.map { SudokuCell(it.toSudokuCell(), isFixed = false) }.toMutableList()