package com.yamal.sudoku.game.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yamal.sudoku.R
import com.yamal.sudoku.start.ui.MenuButton

@Composable
fun BackToMenuButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    MenuButton(
        modifier = modifier,
        onClick = onClick,
        textRes = R.string.back_to_menu_button,
        icon = R.drawable.ic_home,
    )
}