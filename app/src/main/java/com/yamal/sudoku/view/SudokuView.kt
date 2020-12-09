package com.yamal.sudoku.view

import com.yamal.sudoku.model.OnlyReadBoard

interface SudokuView {
    fun updateBoard(board: OnlyReadBoard)
    fun onResetGame(board: OnlyReadBoard)
    fun onGameStarted()
    fun onGameFinished()
}