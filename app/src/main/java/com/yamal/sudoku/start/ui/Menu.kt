package com.yamal.sudoku.start.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
            Button(
                onClick = onContinueGame,
                textRes = R.string.load_game_button
            )
        }
        Button(
            onClick = onStartNewEasyGame,
            textRes = R.string.difficulty_easy
        )
        Button(
            onClick = onStartNewMediumGame,
            textRes = R.string.difficulty_medium
        )
        Button(
            onClick = onStartNewHardGame,
            textRes = R.string.difficulty_hard
        )
    }
}

@Composable
private fun Button(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @StringRes textRes: Int,
) {
    OutlinedButton(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        onClick = onClick
    ) {
        Text(text = stringResource(id = textRes))
    }
}