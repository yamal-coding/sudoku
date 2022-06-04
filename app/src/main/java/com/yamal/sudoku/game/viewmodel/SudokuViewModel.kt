package com.yamal.sudoku.game.viewmodel

import com.yamal.sudoku.commons.thread.CoroutineDispatcherProvider
import com.yamal.sudoku.game.domain.Game
import com.yamal.sudoku.game.domain.Board
import com.yamal.sudoku.game.status.domain.GetSavedBoard
import com.yamal.sudoku.game.level.domain.LoadNewBoard
import com.yamal.sudoku.game.status.domain.RemoveSavedBoard
import com.yamal.sudoku.game.status.domain.SaveBoard
import com.yamal.sudoku.model.SudokuCellValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SudokuViewModel @Inject constructor(
    private val getSavedBoard: GetSavedBoard,
    private val saveBoard: SaveBoard,
    private val removeSavedBoard: RemoveSavedBoard,
    private val loadNewBoard: LoadNewBoard,
    private val dispatchers: CoroutineDispatcherProvider
) {

    private val job = Job()
    private val scope = CoroutineScope(job + dispatchers.mainDispatcher)

    private lateinit var game: Game

    private val board: Board
        get() = game.currentBoard
    private var gameFinished = false

    private val _state = MutableStateFlow<SudokuViewState>(SudokuViewState.Loading)
    val state: StateFlow<SudokuViewState> = _state

    fun onCreate(
        isNewGame: Boolean
    ) {
        when {
            isNewGame -> startNewGame()
            else -> lookForSavedBoard()
        }
    }

    private fun startNewGame() {
        scope.launch {
            val newLevel = withContext(dispatchers.ioDispatcher) {
                loadNewBoard()
            }
            newLevel?.let {
                onGameLoaded(it.board)
                saveBoard()
            } // TODO handle error scenario when a board is not returned
        }
    }

    private fun lookForSavedBoard() {
        scope.launch {
            val savedBoard = getSavedBoard()

            if (savedBoard != null) {
                onGameLoaded(savedBoard)
            } // else // TODO handle error scenario when a saved board is not returned
        }
    }

    private fun onGameLoaded(savedBoard: Board) {
        game = Game(savedBoard)
        _state.value = SudokuViewState.NewGameLoaded(board)
    }

    fun onCellSelected(x: Int, y: Int) {
        if (!gameFinished && (!board[x, y].isFixed)) {
            game.selectCell(x, y)
            _state.value = SudokuViewState.UpdateBoard(
                board,
                selectedRow = x,
                selectedColumn = y
            )
        }
    }

    fun selectNumber(value: SudokuCellValue) {
        if (!gameFinished) {
            game.setSelectedCell(value)
            _state.value = SudokuViewState.UpdateBoard(
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
            _state.value = SudokuViewState.GameFinished
            removeSavedBoard()
        } else {
            saveBoard()
        }
    }

    private fun saveBoard() {
        saveBoard(game.currentBoard)
    }

    fun onDestroy() {
        job.cancel()
    }
}
