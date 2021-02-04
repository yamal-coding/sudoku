package com.yamal.sudoku.game.presenter

import com.yamal.sudoku.commons.thread.CoroutineDispatcherProvider
import com.yamal.sudoku.game.domain.GetSavedBoard
import com.yamal.sudoku.game.domain.RemoveSavedBoard
import com.yamal.sudoku.game.domain.SaveBoard
import com.yamal.sudoku.game.view.SudokuView
import com.yamal.sudoku.model.Board
import com.yamal.sudoku.model.SudokuCellValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SudokuPresenter(
    private val getSavedBoard: GetSavedBoard,
    private val saveBoard: SaveBoard,
    private val removeSavedBoard: RemoveSavedBoard,
    dispatchers: CoroutineDispatcherProvider
) {

    private val job = Job()
    private val scope = CoroutineScope(job + dispatchers.mainDispatcher)

    private lateinit var view: SudokuView
    private lateinit var board: Board
    private var isSetUpMode = true
    private var gameFinished = false

    fun onCreate(isNewGame: Boolean, view: SudokuView) {
        this.view = view

        if (isNewGame) {
            onNewGame()
        } else {
            lookForSavedBoard()
        }
    }

    private fun lookForSavedBoard() {
        scope.launch {
            val savedBoard = getSavedBoard()

            if (savedBoard == null) {
                onNewGame()
            } else {
                onSavedGame(savedBoard)
            }
        }
    }

    private fun onNewGame() {
        board = Board.empty()
        view.onNewGame()
        view.updateBoard(board)
    }

    private fun onSavedGame(savedBoard: Board) {
        board = savedBoard
        view.onSavedGame()
        isSetUpMode = false
        view.updateBoard(board)
    }

    fun setUpFinishedGame() {
        isSetUpMode = false
        view.onSetUpFinished()
    }

    fun checkGame() {
        if (!gameFinished && board.isSolved()) {
            gameFinished = true
            view.onGameFinished()
            removeSavedBoard()
        }
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
            }
        }
    }

    fun saveGame() {
        saveBoard(board)
    }

    fun onDestroy() {
        job.cancel()
    }
}
