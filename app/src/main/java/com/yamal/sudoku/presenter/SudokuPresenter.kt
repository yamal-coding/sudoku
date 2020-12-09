package com.yamal.sudoku.presenter

import com.yamal.sudoku.commons.JobDispatcher
import com.yamal.sudoku.model.Board
import com.yamal.sudoku.model.SudokuCellValue
import com.yamal.sudoku.usecase.GetSavedBoard
import com.yamal.sudoku.usecase.SaveBoard
import com.yamal.sudoku.view.SudokuView

class SudokuPresenter(
    private val getSavedBoard: GetSavedBoard,
    private val saveBoard: SaveBoard,
    private val jobDispatcher: JobDispatcher
) {

    private lateinit var view: SudokuView
    private lateinit var board: Board
    private var isSetUpMode = true
    private var gameFinished = false

    fun onCreate(view: SudokuView) {
        this.view = view

        // TODO show spinner

        getSavedBoard { savedBoard ->
            jobDispatcher.runOnUIThread {
                if (savedBoard == null) {
                    board = Board.empty()
                    view.onNewGame()
                } else {
                    board = savedBoard
                    view.onSavedGame()
                    isSetUpMode = false
                }
                view.updateBoard(board)
            }
        }
    }

    fun setUpFinishedGame() {
        isSetUpMode = false
        view.onSetUpFinished()
    }

    fun checkGame() {
        if (!gameFinished && board.isSolved()) {
            gameFinished = true
            view.onGameFinished()
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
}
