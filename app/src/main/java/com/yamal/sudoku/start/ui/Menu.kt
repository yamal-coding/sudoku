package com.yamal.sudoku.start.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.yamal.sudoku.R
import com.yamal.sudoku.commons.ui.MenuButton
import com.yamal.sudoku.commons.ui.MenuDivider
import com.yamal.sudoku.game.ui.DifficultyViewData

@Composable
fun Menu(
    modifier: Modifier = Modifier,
    shouldShowContinueButton: Boolean,
    onContinueGame: () -> Unit,
    onNewGame: (DifficultyViewData) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (shouldShowContinueButton) {
            MenuDivider()
            MenuButton(
                onClick = onContinueGame,
                textRes = R.string.load_game_button
            )
        }
        MenuDivider()
        NewEasyGameButton(onClick = onNewGame)
        MenuDivider()
        NewMediumGameButton(onClick = onNewGame)
        MenuDivider()
        NewHardGameButton(onClick = onNewGame)
        MenuDivider()
    }
}

@Composable
fun NewEasyGameButton(
    modifier: Modifier = Modifier,
    onClick: (DifficultyViewData) -> Unit,
) {
    MenuButton(
        modifier = modifier,
        onClick = { onClick(DifficultyViewData.EASY) },
        textRes = R.string.difficulty_easy
    )
}

@Composable
fun NewMediumGameButton(
    modifier: Modifier = Modifier,
    onClick: (DifficultyViewData) -> Unit,
) {
    MenuButton(
        modifier = modifier,
        onClick = { onClick(DifficultyViewData.MEDIUM) },
        textRes = R.string.difficulty_medium
    )
}

@Composable
fun NewHardGameButton(
    modifier: Modifier = Modifier,
    onClick: (DifficultyViewData) -> Unit,
) {
    MenuButton(
        modifier = modifier,
        onClick = { onClick(DifficultyViewData.HARD) },
        textRes = R.string.difficulty_hard
    )
}
