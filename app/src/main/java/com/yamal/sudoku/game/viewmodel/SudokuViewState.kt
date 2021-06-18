package com.yamal.sudoku.game.viewmodel

import com.yamal.sudoku.model.ReadOnlyBoard

sealed class SudokuViewState {
    object Loading : SudokuViewState()
    data class SettingUpNewGame(val initialBoard: ReadOnlyBoard) : SudokuViewState()
    data class NewGameLoaded(val board: ReadOnlyBoard): SudokuViewState()
    class UpdateBoard(val board: ReadOnlyBoard): SudokuViewState()
    object SetUpFinished : SudokuViewState()
    object GameFinished : SudokuViewState()
}
