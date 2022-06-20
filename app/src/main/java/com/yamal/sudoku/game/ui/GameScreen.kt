package com.yamal.sudoku.game.ui

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.yamal.sudoku.commons.ui.BackButton
import com.yamal.sudoku.commons.ui.SudokuTopBar
import com.yamal.sudoku.game.viewmodel.SudokuViewModel

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

    }
}