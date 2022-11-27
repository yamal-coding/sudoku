package com.yamal.sudoku.game.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
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
fun PortraitUpdatedBoard(
    updatedBoard: SudokuViewState.UpdatedBoard,
    timeCounter: String?,
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
                AnimatedVisibility(visible = !updatedBoard.gameHasFinished) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        DifficultyLabel(
                            modifier = Modifier
                                .padding(start = 8.dp),
                            difficulty = updatedBoard.board.difficulty
                        )
                        timeCounter?.let {
                            Text(
                                modifier = Modifier.padding(end = 8.dp),
                                text = it
                            )
                        }
                    }
                }
                Board(
                    updatedBoard = updatedBoard,
                    onCellSelected = onCellSelected
                )
                AnimatedVisibility(visible = !updatedBoard.gameHasFinished) {
                    Column {
                        MovementsPad(
                            canUndo = updatedBoard.canUndo,
                            onUndo = onUndo,
                            onClear = onShowClearBoardConfirmationDialog,
                            isPossibilitiesModeEnabled = isPossibilitiesModeEnabled,
                            onEnablePossibilitiesMode = onEnablePossibilitiesMode,
                            onDisablePossibilitiesMode = onDisablePossibilitiesMode,
                            onRemoveCellValue = onRemoveCellValue
                        )
                        RowNumberPad(
                            modifier = Modifier.padding(8.dp),
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ClearButton(
            onClear = onClear,
        )
        UndoButton(
            onUndo = onUndo,
            canUndo = canUndo
        )
        RemoveCellButton(
            onRemoveCellValue = onRemoveCellValue
        )
        PossibilitiesButton(
            isPossibilitiesModeEnabled = isPossibilitiesModeEnabled,
            onEnablePossibilitiesMode = onEnablePossibilitiesMode,
            onDisablePossibilitiesMode = onDisablePossibilitiesMode
        )
    }
}
