package com.yamal.sudoku.start.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yamal.sudoku.R
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
fun MenuButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @StringRes textRes: Int,
    enabled: Boolean = true,
    @DrawableRes icon: Int? = null,
) {
    TextButton(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        enabled = enabled,
    ) {
        icon?.let {
            Icon(
                modifier = Modifier.padding(horizontal = 8.dp),
                painter = painterResource(id = it),
                contentDescription = null
            )
        }
        Text(text = stringResource(id = textRes))
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

@Composable
fun MenuDivider(
    modifier: Modifier = Modifier
) {
    Divider(modifier = modifier.fillMaxWidth())
}
