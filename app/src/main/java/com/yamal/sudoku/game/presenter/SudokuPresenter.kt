package com.yamal.sudoku.game.presenter

import com.yamal.sudoku.commons.thread.CoroutineDispatcherProvider
import com.yamal.sudoku.game.domain.GetSavedBoard
import com.yamal.sudoku.game.domain.DoNotShowSetUpNewGameHintAgain
import com.yamal.sudoku.game.domain.LoadNewGame
import com.yamal.sudoku.game.domain.RemoveSavedBoard
import com.yamal.sudoku.game.domain.SaveBoard
import com.yamal.sudoku.game.domain.ShouldShowSetUpNewGameHint
import com.yamal.sudoku.game.view.FeedbackFactory
import com.yamal.sudoku.game.view.SudokuView
import com.yamal.sudoku.model.Board
import com.yamal.sudoku.model.SudokuCellValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
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

    private lateinit var view: SudokuView
    private lateinit var board: Board
    private var isSetUpMode = true
    private var gameFinished = false

    fun onCreate(
        isSetUpNewGameMode: Boolean,
        isNewGame: Boolean,
        view: SudokuView
    ) {
        this.view = view

        when {
            isNewGame -> startNewGame()
            isSetUpNewGameMode -> scope.launch { onSetUpNewGameMode() }
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
                // TODO Handle this error case in a better way when there is unexpectedly  no saved game
                onSetUpNewGameMode()
            } else {
                onGameLoaded(savedBoard)
            }
        }
    }

    private suspend fun onSetUpNewGameMode() {
        val shouldShowSetUpHint = shouldShowSetUpNewGameHint()

        board = Board.empty()
        view.onSetUpNewGameMode()
        view.updateBoard(board)

        if (shouldShowSetUpHint && feedbackFactory.showSetUpNewGameHintDialog()) {
            doNotShowSetUpNewGameHintAgain()
        }
    }

    private fun onGameLoaded(savedBoard: Board) {
        board = savedBoard
        isSetUpMode = false
        view.updateBoard(board)
        view.onSavedGame()
    }

    fun finishSetUpAndStartGame() {
        isSetUpMode = false
        view.onSetUpFinished()
        saveBoard()
    }

    fun onCellSelected(x: Int, y: Int) {
        if (!gameFinished && (!board[x, y].isFixed || isSetUpMode)) {
            board.selectCell(x, y)
            view.updateBoard(board)
        }
    }

    fun selectNumber(value: SudokuCellValue) {
        if (!gameFinished) {
            if (isSetUpMode) {
                board.fixSelectedCell(value)
                view.updateBoard(board)
            } else if (!board.selectedCell.isFixed) {
                board.setSelectedCell(value)
                view.updateBoard(board)

                checkGame()
            }
        }
    }

    private fun checkGame() {
        if (board.isSolved()) {
            gameFinished = true
            view.onGameFinished()
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
