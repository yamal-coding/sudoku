package com.yamal.sudoku.start.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.yamal.sudoku.R
import com.yamal.sudoku.commons.ui.MenuButton
import com.yamal.sudoku.commons.ui.MenuDivider
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.start.StartScreenTestTags
import com.yamal.sudoku.start.ui.viewmodel.ExistingGameViewData

@Composable
fun Menu(
    modifier: Modifier = Modifier,
    existingGame: ExistingGameViewData?,
    onContinueGame: (existingGame: ExistingGameViewData) -> Unit,
    onNewGame: (Difficulty) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (existingGame != null) {
            MenuDivider()
            MenuButton(
                onClick = { onContinueGame(existingGame) },
                textRes = R.string.load_game_button,
                testTag = StartScreenTestTags.CONTINUE_GAME_BUTTON,
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
    onClick: (Difficulty) -> Unit,
) {
    MenuButton(
        modifier = modifier,
        onClick = { onClick(Difficulty.EASY) },
        textRes = R.string.difficulty_easy
    )
}

@Composable
fun NewMediumGameButton(
    modifier: Modifier = Modifier,
    onClick: (Difficulty) -> Unit,
) {
    MenuButton(
        modifier = modifier,
        onClick = { onClick(Difficulty.MEDIUM) },
        textRes = R.string.difficulty_medium
    )
}

@Composable
fun NewHardGameButton(
    modifier: Modifier = Modifier,
    onClick: (Difficulty) -> Unit,
) {
    MenuButton(
        modifier = modifier,
        onClick = { onClick(Difficulty.HARD) },
        textRes = R.string.difficulty_hard
    )
}
