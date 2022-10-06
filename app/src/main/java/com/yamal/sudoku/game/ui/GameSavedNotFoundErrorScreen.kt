package com.yamal.sudoku.game.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yamal.sudoku.R
import com.yamal.sudoku.commons.ui.Header
import com.yamal.sudoku.start.ui.MenuButton
import com.yamal.sudoku.start.ui.MenuDivider

@Composable
fun GameSavedNotFoundErrorScreen(
    onBackToMenu: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Header(
            modifier = Modifier.padding(horizontal = 20.dp),
            icon = R.drawable.ic_error,
            title = R.string.saved_game_not_found_error_title,
            subtitle = R.string.saved_game_not_found_error_subtitle
        )
        Column(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth(fraction = 0.7F)
        ) {
            MenuDivider()
            MenuButton(
                onClick = onBackToMenu,
                textRes = R.string.back_to_menu_button
            )
            MenuDivider()
        }
    }
}