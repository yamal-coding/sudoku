package com.yamal.sudoku.main

import android.content.Context
import android.content.Intent
import com.yamal.sudoku.game.view.SudokuActivity

class Navigator(
    private val context: Context
) {
    fun openNewGame() {
        // TODO
    }

    fun openExistingGameSetUp() {
        openSudokuGame(isSetUpNewGameMode = true)
    }

    fun openSavedGame() {
        openSudokuGame(isSetUpNewGameMode = false)
    }

    private fun openSudokuGame(isSetUpNewGameMode: Boolean) {
        val intent = Intent(context, SudokuActivity::class.java).apply {
            putExtra(SudokuActivity.IS_SET_UP_GAME_MODE_EXTRA, isSetUpNewGameMode)
        }

        context.startActivity(intent)
    }
}
