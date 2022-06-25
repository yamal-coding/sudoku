package com.yamal.sudoku.game.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yamal.sudoku.game.domain.Game
import com.yamal.sudoku.game.domain.Board
import com.yamal.sudoku.game.status.domain.GetSavedBoard
import com.yamal.sudoku.game.level.domain.LoadNewBoard
import com.yamal.sudoku.game.status.domain.RemoveSavedBoard
import com.yamal.sudoku.game.status.domain.SaveBoard
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

    private val board: Board
        get() = game.currentBoard
    private var gameFinished = false

    private val _state = MutableStateFlow<SudokuViewState>(SudokuViewState.Idle)
    val state: Flow<SudokuViewState> = _state

    fun initGame(
        isNewGame: Boolean
    ) {
        if (gameIsNotInitialized()) {
            when {
                isNewGame -> startNewGame()
                else -> lookForSavedBoard()
            }
        }
    }

    private fun gameIsNotInitialized(): Boolean =
        _state.compareAndSet(expect = SudokuViewState.Idle, update = SudokuViewState.Loading)

    private fun startNewGame() {
        viewModelScope.launch {
            val newLevel = loadNewBoard()
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
        _state.value = SudokuViewState.UpdatedBoard(
            board,
            selectedRow = null,
            selectedColumn = null
        )
    }

    fun onCellSelected(x: Int, y: Int) {
        if (!gameFinished && game.selectCell(x, y)) {
            _state.value = SudokuViewState.UpdatedBoard(
                board,
                selectedRow = x,
                selectedColumn = y
            )
        }
    }

    fun selectNumber(value: SudokuCellValue) {
        if (!gameFinished) {
            game.setSelectedCell(value)
            _state.value = SudokuViewState.UpdatedBoard(
                board,
                selectedRow = game.selectedRow,
                selectedColumn = game.selectedColumn
            )
            checkGame()
        }
    }

    private fun checkGame() {
        if (game.isSolved()) {
            gameFinished = true
            _state.value = SudokuViewState.GameFinished(board)
            removeSavedBoard()
        } else {
            saveBoard()
        }
    }

    private fun saveBoard() {
        saveBoard(game.currentBoard)
    }
}
