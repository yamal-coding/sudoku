package com.yamal.sudoku.game.view

import com.yamal.sudoku.model.ReadOnlyBoard

interface SudokuView {
    fun onNewGame()
    fun onSavedGame()
    fun updateBoard(onlyBoard: ReadOnlyBoard)
    fun onResetGame(onlyBoard: ReadOnlyBoard)
    fun onSetUpFinished()
    fun onGameFinished()
}
