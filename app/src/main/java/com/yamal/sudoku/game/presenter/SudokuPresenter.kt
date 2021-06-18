package com.yamal.sudoku.game.presenter

import com.yamal.sudoku.commons.thread.CoroutineDispatcherProvider
import com.yamal.sudoku.game.domain.GetSavedBoard
import com.yamal.sudoku.game.domain.DoNotShowSetUpNewGameHintAgain
import com.yamal.sudoku.game.domain.LoadNewGame
import com.yamal.sudoku.game.domain.RemoveSavedBoard
import com.yamal.sudoku.game.domain.SaveBoard
import com.yamal.sudoku.game.domain.ShouldShowSetUpNewGameHint
import com.yamal.sudoku.game.view.FeedbackFactory
import com.yamal.sudoku.model.Board
import com.yamal.sudoku.model.SudokuCellValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SudokuPresenter @Inject constructor(
    private val getSavedBoard: GetSavedBoard,
    private val saveBoard: SaveBoard,
    private val removeSavedBoard: RemoveSavedBoard,
    private val loadNewGame: LoadNewGame,
    private val feedbackFactory: FeedbackFactory,
    private val shouldShowSetUpNewGameHint: ShouldShowSetUpNewGameHint,
    private val doNotShowSetUpNewGameHintAgain: DoNotShowSetUpNewGameHintAgain,
    dispatchers: CoroutineDispatcherProvider
) {

    private val job = Job()
    private val scope = CoroutineScope(job + dispatchers.mainDispatcher)

    private lateinit var board: Board
    private var isSetUpMode = true
    private var gameFinished = false

    private val _state = MutableStateFlow<SudokuViewState>(SudokuViewState.Loading)
    val state: StateFlow<SudokuViewState> = _state

    fun onCreate(
        isSetUpNewGameMode: Boolean,
        isNewGame: Boolean
    ) {
        when {
            isNewGame -> startNewGame()
            isSetUpNewGameMode -> scope.launch { onSettingUpNewGame() }
            else -> lookForSavedBoard()
        }
    }

    private fun startNewGame() {
        scope.launch {
            val newBoard = loadNewGame()
            onGameLoaded(newBoard)
            saveBoard()
        }
    }

    private fun lookForSavedBoard() {
        scope.launch {
            val savedBoard = getSavedBoard()

            if (savedBoard == null) {
                onSettingUpNewGame()
            } else {
                onGameLoaded(savedBoard)
            }
        }
    }

    private suspend fun onSettingUpNewGame() {
        val shouldShowSetUpHint = shouldShowSetUpNewGameHint()

        board = Board.empty()
        _state.value = SudokuViewState.SettingUpNewGame(board)

        if (shouldShowSetUpHint && feedbackFactory.showSetUpNewGameHintDialog()) {
            doNotShowSetUpNewGameHintAgain()
        }
    }

    private fun onGameLoaded(savedBoard: Board) {
        board = savedBoard
        isSetUpMode = false
        _state.value = SudokuViewState.NewGameLoaded(board)
    }

    fun finishSetUpAndStartGame() {
        isSetUpMode = false
        _state.value = SudokuViewState.SetUpFinished
        saveBoard()
    }

    fun onCellSelected(x: Int, y: Int) {
        if (!gameFinished && (!board[x, y].isFixed || isSetUpMode)) {
            board.selectCell(x, y)
            _state.value = SudokuViewState.UpdateBoard(board)
        }
    }

    fun selectNumber(value: SudokuCellValue) {
        if (!gameFinished) {
            if (isSetUpMode) {
                board.fixSelectedCell(value)
                _state.value = SudokuViewState.UpdateBoard(board)
            } else if (!board.selectedCell.isFixed) {
                board.setSelectedCell(value)
                _state.value = SudokuViewState.UpdateBoard(board)

                checkGame()
            }
        }
    }

    private fun checkGame() {
        if (board.isSolved()) {
            gameFinished = true
            _state.value = SudokuViewState.GameFinished
            removeSavedBoard()
        } else {
            saveBoard()
        }
    }

    private fun saveBoard() {
        saveBoard(board)
    }

    fun onDestroy() {
        job.cancel()
    }
}
