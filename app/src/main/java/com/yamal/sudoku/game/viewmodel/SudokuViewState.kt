package com.yamal.sudoku.game.viewmodel

import com.yamal.sudoku.game.domain.ReadOnlyBoard

sealed class SudokuViewState {
    object Loading : SudokuViewState()
    class NewGameLoaded(val board: ReadOnlyBoard): SudokuViewState()
    class UpdateBoard(
        val board: ReadOnlyBoard,
        val selectedRow: Int?,
        val selectedColumn: Int?,
    ): SudokuViewState()
    object SetUpFinished : SudokuViewState()
    object GameFinished : SudokuViewState()
}
