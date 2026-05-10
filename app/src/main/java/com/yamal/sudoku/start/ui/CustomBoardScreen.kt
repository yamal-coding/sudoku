package com.yamal.sudoku.start.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yamal.sudoku.R
import com.yamal.sudoku.commons.ui.Dialog
import com.yamal.sudoku.commons.ui.MenuButton
import com.yamal.sudoku.commons.ui.animation.SlideInVerticalTransition
import com.yamal.sudoku.commons.utils.ScreenDimensions
import com.yamal.sudoku.game.navigation.GameNavigationParams
import com.yamal.sudoku.game.domain.BOARD_SIDE
import com.yamal.sudoku.game.domain.ReadOnlyBoard
import com.yamal.sudoku.game.ui.RowNumberPad
import com.yamal.sudoku.game.ui.SudokuBoard
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.model.SudokuCell
import com.yamal.sudoku.model.SudokuCellValue
import com.yamal.sudoku.start.ui.viewmodel.CustomBoardState
import com.yamal.sudoku.start.ui.viewmodel.CustomBoardViewModel

@Composable
fun CustomBoardScreen(
    viewModel: CustomBoardViewModel,
    onStartGame: (GameNavigationParams) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    if (state.showStartGameDialog) {
        Dialog(
            title = stringResource(id = R.string.custom_board_start_dialog_title),
            subtitle = stringResource(id = R.string.custom_board_start_dialog_subtitle),
            onDismissRequest = viewModel::onDismissStartGameDialog,
            rightButtonText = stringResource(id = R.string.custom_board_start_dialog_ok_button),
            onRightButtonClick = {
                viewModel.onDismissStartGameDialog()
                viewModel.saveAndStartGame(onStartGame)
            },
            leftButtonText = stringResource(id = R.string.custom_board_start_dialog_cancel_button),
            onLeftButtonClick = viewModel::onDismissStartGameDialog,
        )
    }

    val orientation = LocalConfiguration.current.orientation
    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        PortraitCustomBoard(
            state = state,
            onCellSelected = viewModel::onCellSelected,
            onNumberSelected = viewModel::onNumberSelected,
            onRemoveCellValue = { viewModel.onNumberSelected(SudokuCellValue.EMPTY) },
            onRequestStartGame = viewModel::onRequestStartGame,
        )
    } else {
        LandscapeCustomBoard(
            state = state,
            onCellSelected = viewModel::onCellSelected,
            onNumberSelected = viewModel::onNumberSelected,
            onRemoveCellValue = { viewModel.onNumberSelected(SudokuCellValue.EMPTY) },
            onRequestStartGame = viewModel::onRequestStartGame,
        )
    }
}

@Suppress("MagicNumber")
@Composable
private fun PortraitCustomBoard(
    state: CustomBoardState,
    onCellSelected: (Int, Int) -> Unit,
    onNumberSelected: (SudokuCellValue) -> Unit,
    onRemoveCellValue: () -> Unit,
    onRequestStartGame: () -> Unit,
) {
    SlideInVerticalTransition {
        Box(modifier = Modifier.fillMaxSize()) {
            val configuration = LocalConfiguration.current
            val portraitModifier = if (configuration.screenWidthDp.dp > ScreenDimensions.SMALL_DEVICE_MAX_WIDTH.dp) {
                Modifier.fillMaxWidth(fraction = 0.8F)
            } else {
                Modifier
            }
            Column(
                modifier = portraitModifier
                    .fillMaxHeight()
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
            ) {
                SudokuBoard(
                    modifier = Modifier.fillMaxWidth(),
                    board = state.toReadOnlyBoard(),
                    selectedRow = state.selectedRow,
                    selectedColumn = state.selectedColumn,
                    gameHasFinished = false,
                    onCellSelected = onCellSelected,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RemoveCellValueButton(onClick = onRemoveCellValue)
                }
                RowNumberPad(
                    modifier = Modifier.padding(8.dp),
                    onValueSelected = onNumberSelected,
                )
                MenuButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = onRequestStartGame,
                    textRes = R.string.custom_board_start_game_button,
                )
            }
        }
    }
}

@Composable
private fun LandscapeCustomBoard(
    state: CustomBoardState,
    onCellSelected: (Int, Int) -> Unit,
    onNumberSelected: (SudokuCellValue) -> Unit,
    onRemoveCellValue: () -> Unit,
    onRequestStartGame: () -> Unit,
) {
    SlideInVerticalTransition {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SudokuBoard(
                modifier = Modifier
                    .weight(1F)
                    .padding(8.dp),
                board = state.toReadOnlyBoard(),
                selectedRow = state.selectedRow,
                selectedColumn = state.selectedColumn,
                gameHasFinished = false,
                onCellSelected = onCellSelected,
            )
            Column(
                modifier = Modifier
                    .weight(1F)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                com.yamal.sudoku.game.ui.GridNumberPad(
                    onValueSelected = onNumberSelected,
                )
                RemoveCellValueButton(onClick = onRemoveCellValue)
                MenuButton(
                    modifier = Modifier.padding(top = 8.dp),
                    onClick = onRequestStartGame,
                    textRes = R.string.custom_board_start_game_button,
                )
            }
        }
    }
}

@Composable
private fun RemoveCellValueButton(onClick: () -> Unit) {
    com.yamal.sudoku.commons.ui.IconButton(
        onClick = onClick,
        iconRes = R.drawable.ic_remove,
        textRes = R.string.remove_button,
    )
}

private fun CustomBoardState.toReadOnlyBoard(): ReadOnlyBoard =
    object : ReadOnlyBoard {
        override val difficulty: Difficulty = Difficulty.CUSTOM
        override fun get(row: Int, col: Int): SudokuCell = cells[row * BOARD_SIDE + col]
    }
