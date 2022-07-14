package com.yamal.sudoku.game.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yamal.sudoku.game.domain.Game
import com.yamal.sudoku.game.domain.Board
import com.yamal.sudoku.game.status.domain.GetSavedBoard
import com.yamal.sudoku.game.level.domain.LoadNewBoard
import com.yamal.sudoku.game.status.domain.RemoveSavedBoard
import com.yamal.sudoku.game.status.domain.SaveBoard
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.model.SudokuCellValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SudokuViewModel @Inject constructor(
    private val getSavedBoard: GetSavedBoard,
    private val saveBoard: SaveBoard,
    private val removeSavedBoard: RemoveSavedBoard,
    private val loadNewBoard: LoadNewBoard,
) : ViewModel() {

    private lateinit var game: Game
    private var gameFinished = false

    private val _state = MutableStateFlow<SudokuViewState>(SudokuViewState.Idle)
    val state: Flow<SudokuViewState> = _state

    private val _shouldShowClearBoardConfirmationDialog = MutableStateFlow(false)
    val shouldShowClearBoardConfirmationDialog: Flow<Boolean> =
        _shouldShowClearBoardConfirmationDialog

    fun initNewGame(difficulty: Difficulty) {
        onGameNotInitialized {
            startNewGame(difficulty)
        }
    }

    fun initExistingGame() {
        onGameNotInitialized {
            lookForSavedBoard()
        }
    }

    private inline fun onGameNotInitialized(block: () -> Unit) {
        if (_state.compareAndSet(expect = SudokuViewState.Idle, update = SudokuViewState.Loading)) {
            block()
        }
    }

    private fun startNewGame(difficulty: Difficulty) {
        viewModelScope.launch {
            val newLevel = loadNewBoard(difficulty)
            if (newLevel != null) {
                onGameLoaded(newLevel.board)
                saveBoard()
            } else {
                onNewBoardNotFound()
            }
        }
    }

    private fun onNewBoardNotFound() {
        _state.value = SudokuViewState.NewBoardNotFound
    }

    private fun lookForSavedBoard() {
        viewModelScope.launch {
            val savedBoard = getSavedBoard()

            if (savedBoard != null) {
                onGameLoaded(savedBoard)
            } else {
                onSavedGameNotFound()
            }
        }
    }

    private fun onSavedGameNotFound() {
        _state.value = SudokuViewState.SavedGameNotFound
    }

    private fun onGameLoaded(savedBoard: Board) {
        game = Game(savedBoard)
        updateBoard(x = null, y = null)
    }

    fun onCellSelected(x: Int, y: Int) {
        if (!gameFinished && game.selectCell(x, y)) {
            updateBoard(x = x, y = y)
        }
    }

    fun selectNumber(value: SudokuCellValue) {
        if (!gameFinished) {
            game.setSelectedCell(value)
            updateBoard(x = game.selectedRow, y = game.selectedColumn)
            checkGame()
        }
    }

    fun undo() {
        if (!gameFinished) {
            game.undo()
            updateBoard(x = game.selectedRow, y = game.selectedColumn)
            saveBoard()
        }
    }

    fun showClearBoardConfirmationDialog() {
        _shouldShowClearBoardConfirmationDialog.value = true
    }

    fun hideClearBoardConfirmationDialog() {
        _shouldShowClearBoardConfirmationDialog.value = false
    }

    fun clear() {
        hideClearBoardConfirmationDialog()

        if (!gameFinished) {
            game.clear()
            updateBoard(x = game.selectedRow, y = game.selectedColumn)
            saveBoard()
        }
    }

    private fun checkGame() {
        if (game.isSolved()) {
            gameFinished = true
            _state.value = SudokuViewState.GameFinished(game.currentBoard)
            removeSavedBoard()
        } else {
            saveBoard()
        }
    }

    private fun saveBoard() {
        saveBoard(game.currentBoard)
    }

    private fun updateBoard(x: Int?, y: Int?) {
        _state.value = SudokuViewState.UpdatedBoard(
            game.currentBoard,
            selectedRow = x,
            selectedColumn = y,
            canUndo = game.canUndo
        )
    }
}
