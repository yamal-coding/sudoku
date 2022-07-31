package com.yamal.sudoku.game.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Board(
            modifier = Modifier.fillMaxHeight(),
            updatedBoard = updatedBoard,
            onCellSelected = onCellSelected
        )
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
    Column {
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
