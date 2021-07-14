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
        openSudokuGame(isSetUpNewGameMode = false, isNewGame = true)
    }

    fun openExistingGameSetUp() {
        openSudokuGame(isSetUpNewGameMode = true, isNewGame = false)
    }

    fun openSavedGame() {
        openSudokuGame(isSetUpNewGameMode = false, isNewGame = false
    }

    private fun openSudokuGame(isSetUpNewGameMode: Boolean, isNewGame: Boolean) {
        val intent = Intent(context, SudokuActivity::class.java).apply {
            putExtra(SudokuActivity.IS_SET_UP_GAME_MODE_EXTRA, isSetUpNewGameMode)
            putExtra(SudokuActivity.IS_NEW_GAME_EXTRA, isNewGame)
        }

        context.startActivity(intent)
    }
}
