package com.yamal.sudoku.game.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yamal.sudoku.commons.ui.BackButton
import com.yamal.sudoku.commons.ui.SudokuTopBar
import com.yamal.sudoku.game.viewmodel.SudokuViewModel
import com.yamal.sudoku.game.viewmodel.SudokuViewState
import com.yamal.sudoku.model.SudokuCellValue

@Composable
fun GameScreen(
    isNewGame: Boolean,
    viewModel: SudokuViewModel,
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
            viewModel.initGame(isNewGame = isNewGame)
        }

        val state by viewModel.state.collectAsState(initial = SudokuViewState.Idle)
        when (state) {
            is SudokuViewState.Idle -> {}
            is SudokuViewState.Loading -> {}
            is SudokuViewState.NewBoardNotFound -> { /* TODO */ }
            is SudokuViewState.SavedGameNotFound -> { /* TODO */ }
            is SudokuViewState.GameFinished -> { /* TODO */ }
            is SudokuViewState.UpdatedBoard -> {
                UpdatedBoard(
                    updatedBoard = state as SudokuViewState.UpdatedBoard,
                    onCellSelected = viewModel::onCellSelected,
                    onValueSelected = viewModel::selectNumber,
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
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        SudokuBoard(
            modifier = Modifier.padding(8.dp),
            board = updatedBoard.board,
            selectedRow = updatedBoard.selectedRow,
            selectedColumn = updatedBoard.selectedColumn,
            onCellSelected = onCellSelected,
        )
        NumberPad(
            modifier = Modifier.padding(8.dp),
            onValueSelected = onValueSelected
        )
    }
}
