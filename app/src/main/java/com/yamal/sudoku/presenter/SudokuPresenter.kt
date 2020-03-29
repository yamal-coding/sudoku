package com.yamal.sudoku.presenter

import com.yamal.sudoku.model.Board
import com.yamal.sudoku.model.SudokuCellValue
import com.yamal.sudoku.view.SudokuView

class SudokuPresenter {

    private lateinit var view: SudokuView
    private lateinit var board: Board
    private var isSetUpMode = true
    private var gameFinished = false

    fun onCreate(view: SudokuView) {
        board = Board.empty()
        this.view = view
        view.updateBoard(board)
    }

    fun startGame() {
        isSetUpMode = false
        view.onGameStarted()
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
}