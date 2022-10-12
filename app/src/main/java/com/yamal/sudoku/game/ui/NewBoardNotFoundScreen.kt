package com.yamal.sudoku.game.ui

import androidx.compose.runtime.Composable
import com.yamal.sudoku.R

@Composable
fun NewBoardNotFoundScreen(
    onBackToMenu: () -> Unit
) {
    GameErrorScreen(
        titleRes = R.string.new_board_not_found_error_title,
        subtitleRes = R.string.new_board_not_found_error_subtitle,
        onBackToMenu = onBackToMenu
    )
}