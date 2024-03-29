package com.yamal.sudoku.game.ui

import androidx.compose.runtime.Composable
import com.yamal.sudoku.R

@Composable
fun GameSavedNotFoundErrorScreen(
    onBackToMenu: () -> Unit
) {
    GameErrorScreen(
        titleRes = R.string.saved_game_not_found_error_title,
        subtitleRes = R.string.saved_game_not_found_error_subtitle,
        onBackToMenu = onBackToMenu
    )
}
