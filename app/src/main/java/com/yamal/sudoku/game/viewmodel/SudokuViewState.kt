package com.yamal.sudoku.game.viewmodel

import com.yamal.sudoku.game.domain.ReadOnlyBoard

sealed class SudokuViewState {
    object Idle : SudokuViewState()
    object Loading : SudokuViewState()
    data class UpdatedBoard(
        val board: ReadOnlyBoard,
        val selectedRow: Int?,
        val selectedColumn: Int?,
        val canUndo: Boolean,
    ) : SudokuViewState()
    data class GameFinished(
        val board: ReadOnlyBoard,
    ) : SudokuViewState()
    object SavedGameNotFound : SudokuViewState()
    object NewBoardNotFound : SudokuViewState()
}
