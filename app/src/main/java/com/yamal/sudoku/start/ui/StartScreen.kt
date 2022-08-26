package com.yamal.sudoku.start.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yamal.sudoku.R
import com.yamal.sudoku.commons.ui.theme.SudokuTheme
import com.yamal.sudoku.start.ui.viewmodel.StartScreenState
import com.yamal.sudoku.start.ui.viewmodel.StartViewModel

@Composable
fun StartScreen(
    viewModel: StartViewModel,
    onContinueGame: () -> Unit,
    onStartNewEasyGame: () -> Unit,
    onStartNewMediumGame: () -> Unit,
    onStartNewHardGame: () -> Unit,
) {
    val startScreenState by remember {
        viewModel.startScreenState
    }.collectAsState(initial = StartScreenState.Idle)

    when (startScreenState) {
        is StartScreenState.Idle -> {}
        is StartScreenState.Ready -> {
            StartScreen(
                shouldShowContinueButton = (startScreenState as StartScreenState.Ready).shouldShowContinueButton,
                onContinueGame = onContinueGame,
                onStartNewEasyGame = onStartNewEasyGame,
                onStartNewMediumGame = onStartNewMediumGame,
                onStartNewHardGame = onStartNewHardGame
            )
        }
    }
}

@Composable
private fun StartScreen(
    shouldShowContinueButton: Boolean,
    onContinueGame: () -> Unit,
    onStartNewEasyGame: () -> Unit,
    onStartNewMediumGame: () -> Unit,
    onStartNewHardGame: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxHeight()
                .width(IntrinsicSize.Max),
            verticalArrangement = Arrangement.Center,
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

@Preview
@Composable
private fun StartScreenPreview() {
    SudokuTheme {
        StartScreen(
            shouldShowContinueButton = true,
            onContinueGame = { },
            onStartNewEasyGame = { },
            onStartNewMediumGame = { },
            onStartNewHardGame = { }
        )
    }
}