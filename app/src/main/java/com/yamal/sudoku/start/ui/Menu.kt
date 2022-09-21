package com.yamal.sudoku.start.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.yamal.sudoku.R

@Composable
fun Menu(
    modifier: Modifier = Modifier,
    shouldShowContinueButton: Boolean,
    onContinueGame: () -> Unit,
    onStartNewEasyGame: () -> Unit,
    onStartNewMediumGame: () -> Unit,
    onStartNewHardGame: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (shouldShowContinueButton) {
            ButtonDivider()
            Button(
                onClick = onContinueGame,
                textRes = R.string.load_game_button
            )
        }
        ButtonDivider()
        Button(
            onClick = onStartNewEasyGame,
            textRes = R.string.difficulty_easy
        )
        ButtonDivider()
        Button(
            onClick = onStartNewMediumGame,
            textRes = R.string.difficulty_medium
        )
        ButtonDivider()
        Button(
            onClick = onStartNewHardGame,
            textRes = R.string.difficulty_hard
        )
        ButtonDivider()
    }
}

@Composable
private fun Button(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @StringRes textRes: Int,
) {
    TextButton(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Text(text = stringResource(id = textRes))
    }
}

@Composable
private fun ButtonDivider(
    modifier: Modifier = Modifier
) {
    Divider(modifier = modifier.fillMaxWidth())
}
