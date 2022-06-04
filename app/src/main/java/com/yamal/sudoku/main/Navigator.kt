package com.yamal.sudoku.main

import android.content.Context
import android.content.Intent
import com.yamal.sudoku.game.view.SudokuActivity
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class Navigator @Inject constructor(
    @ActivityContext private val context: Context
) {
    fun openNewGame() {
        openSudokuGame(isNewGame = true)
    }

    fun openSavedGame() {
        openSudokuGame(isNewGame = false)
    }

    private fun openSudokuGame(isNewGame: Boolean) {
        val intent = Intent(context, SudokuActivity::class.java).apply {
            putExtra(SudokuActivity.IS_NEW_GAME_EXTRA, isNewGame)
        }

        context.startActivity(intent)
    }
}
