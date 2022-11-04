package com.yamal.sudoku.game.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.yamal.sudoku.commons.ui.animation.SlideInVerticalTransition
import com.yamal.sudoku.commons.utils.ScreenDimensions
import com.yamal.sudoku.game.viewmodel.SudokuViewState
import com.yamal.sudoku.model.SudokuCellValue

@Composable
fun LandscapeUpdatedBoard(
    updatedBoard: SudokuViewState.UpdatedBoard,
    onCellSelected: (row: Int, column: Int) -> Unit,
    onValueSelected: (SudokuCellValue) -> Unit,
    onUndo: () -> Unit,
    onShowClearBoardConfirmationDialog: () -> Unit,
    isPossibilitiesModeEnabled: Boolean,
    onEnablePossibilitiesMode: () -> Unit,
    onDisablePossibilitiesMode: () -> Unit,
    onRemoveCellValue: () -> Unit,
) {
    SlideInVerticalTransition {
        Box(modifier = Modifier.fillMaxSize()) {
            val configuration = LocalConfiguration.current
            val landscapeModifier = if (configuration.screenHeightDp.dp > ScreenDimensions.SMALL_DEVICE_MAX_WIDTH.dp) {
                Modifier.fillMaxHeight(fraction = 0.8F)
            } else {
                Modifier
            }
            Row(
                modifier = landscapeModifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Board(
                    modifier = Modifier.fillMaxHeight(),
                    updatedBoard = updatedBoard,
                    onCellSelected = onCellSelected
                )
                AnimatedVisibility(visible = !updatedBoard.gameHasFinished) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(start = 8.dp),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        DifficultyLabel(
                            difficulty = updatedBoard.board.difficulty
                        )
                        MovementsPad(
                            canUndo = updatedBoard.canUndo,
                            onUndo = onUndo,
                            onClear = onShowClearBoardConfirmationDialog,
                            isPossibilitiesModeEnabled = isPossibilitiesModeEnabled,
                            onEnablePossibilitiesMode = onEnablePossibilitiesMode,
                            onDisablePossibilitiesMode = onDisablePossibilitiesMode,
                            onRemoveCellValue = onRemoveCellValue
                        )
                        GridNumberPad(
                            modifier = Modifier.wrapContentSize(),
                            onValueSelected = onValueSelected
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MovementsPad(
    canUndo: Boolean,
    onUndo: () -> Unit,
    onClear: () -> Unit,
    isPossibilitiesModeEnabled: Boolean,
    onEnablePossibilitiesMode: () -> Unit,
    onDisablePossibilitiesMode: () -> Unit,
    onRemoveCellValue: () -> Unit,
) {
    Column(
        modifier = Modifier.width(IntrinsicSize.Max),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row {
            ClearButton(
                onClear = onClear,
            )
            UndoButton(
                modifier = Modifier.padding(start = 8.dp),
                onUndo = onUndo,
                canUndo = canUndo
            )
        }
        Row {
            RemoveCellButton(
                onRemoveCellValue = onRemoveCellValue
            )
            PossibilitiesButton(
                modifier = Modifier.padding(start = 8.dp),
                isPossibilitiesModeEnabled = isPossibilitiesModeEnabled,
                onEnablePossibilitiesMode = onEnablePossibilitiesMode,
                onDisablePossibilitiesMode = onDisablePossibilitiesMode
            )
        }
    }
}
