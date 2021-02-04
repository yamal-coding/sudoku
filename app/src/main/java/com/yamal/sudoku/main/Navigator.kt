package com.yamal.sudoku.main

import android.content.Context
import android.content.Intent
import com.yamal.sudoku.game.view.SudokuActivity

class Navigator(
    private val context: Context
) {
    fun openNewGame() {
        openSudokuGame(newGame = true)
    }

    fun openSavedGame() {
        openSudokuGame(newGame = false)
    }

    private fun openSudokuGame(newGame: Boolean) {
        val intent = Intent(context, SudokuActivity::class.java).apply {
            putExtra(SudokuActivity.IS_NEW_GAME_EXTRA, newGame)
        }

        context.startActivity(intent)
    }
}