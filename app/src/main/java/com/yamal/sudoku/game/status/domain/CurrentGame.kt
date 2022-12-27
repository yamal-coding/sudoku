package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.game.domain.Game
import com.yamal.sudoku.game.domain.ReadOnlyBoard
import com.yamal.sudoku.model.SudokuCellValue
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@ViewModelScoped
open class CurrentGame @Inject constructor() {

    open val id: String
        get() = game.id

    private val _gameState = MutableStateFlow<SudokuState>(SudokuState.Idle)
    val gameState: Flow<SudokuState> = _gameState

    private lateinit var game: Game

    open suspend fun onGameStarted(block: suspend () -> Unit) {
        if (_gameState.compareAndSet(expect = SudokuState.Idle, update = SudokuState.Loading)) {
            block()
        }
    }

    open fun getCurrentBoard(): ReadOnlyBoard =
        game.currentBoard

    open fun hasFinished(): Boolean =
        game.isSolved()

    open fun onGameFinished() {
        updateBoard(selectedRow = null, selectedColumn = null, gameHasFinished = true)
    }

    open fun onLoadingGame() {
        _gameState.value = SudokuState.Loading
    }

    open fun onGameReady(game: Game) {
        this.game = game
        updateBoard(selectedRow = null, selectedColumn = null)
    }

    open fun onSelectCell(selectedRow: Int, selectedColumn: Int) {
        if (game.selectCell(row = selectedRow, column = selectedColumn)) {
            updateBoard()
        }
    }

    open fun onSavedGameNotFound() {
        _gameState.value = SudokuState.SavedGameNotFound
    }

    open fun onNewBoardNotFound() {
        _gameState.value = SudokuState.NewBoardNotFound
    }

    open fun onCellUpdated(value: SudokuCellValue) {
        game.setSelectedCell(value)
        updateBoard()
    }

    open fun onPossibilityAddedOrRemoved(possibility: SudokuCellValue) {
        game.addOrRemovePossibleValue(possibleValue = possibility)
        updateBoard()
    }

    open fun onClearBoard() {
        game.clear()
        updateBoard()
    }

    open fun onUndo() {
        game.undo()
        updateBoard()
    }

    private fun updateBoard() {
        updateBoard(selectedRow = game.selectedRow, selectedColumn = game.selectedColumn)
    }

    private fun updateBoard(
        selectedRow: Int?,
        selectedColumn: Int?,
        gameHasFinished: Boolean = false,
    ) {
        _gameState.value = SudokuState.UpdatedBoard(
            board = game.currentBoard,
            selectedRow = selectedRow,
            selectedColumn = selectedColumn,
            canUndo = game.canUndo,
            gameHasFinished = gameHasFinished,
        )
    }
}

sealed class SudokuState {
    object Idle : SudokuState()
    object Loading : SudokuState()
    data class UpdatedBoard(
        val board: ReadOnlyBoard,
        val selectedRow: Int?,
        val selectedColumn: Int?,
        val canUndo: Boolean,
        val gameHasFinished: Boolean,
    ) : SudokuState()
    object SavedGameNotFound : SudokuState()
    object NewBoardNotFound : SudokuState()
}