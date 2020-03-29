package com.yamal.sudoku.view

import com.yamal.sudoku.model.Board

interface SudokuView {
    fun updateBoard(board: Board)
    fun onResetGame(board: Board)
    fun onGameStarted()
    fun onGameFinished()
}