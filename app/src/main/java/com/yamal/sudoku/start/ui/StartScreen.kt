package com.yamal.sudoku.start.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
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
import com.yamal.sudoku.start.ui.viewmodel.StartViewModel

@Composable
fun StartScreen(
    viewModel: StartViewModel,
    onContinueGame: () -> Unit,
    onStartNewEasyGame: () -> Unit,
    onStartNewMediumGame: () -> Unit,
    onStartNewHardGame: () -> Unit,
) {
    val shouldShowContinueButton by remember {
        viewModel.shouldShowContinueButton
    }.collectAsState(initial = false)

    StartScreen(
        shouldShowContinueButton = shouldShowContinueButton,
        onContinueGame = onContinueGame,
        onStartNewEasyGame = onStartNewEasyGame,
        onStartNewMediumGame = onStartNewMediumGame,
        onStartNewHardGame = onStartNewHardGame
    )
}

@Composable
private fun StartScreen(
    shouldShowContinueButton: Boolean,
    onContinueGame: () -> Unit,
    onStartNewEasyGame: () -> Unit,
    onStartNewMediumGame: () -> Unit,
    onStartNewHardGame: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        if (shouldShowContinueButton) {
            Button(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                onClick = onContinueGame
            ) {
                Text(text = stringResource(id = R.string.load_game_button))
            }
        }
        Button(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            onClick = onStartNewEasyGame
        ) {
            Text(text = stringResource(id = R.string.difficulty_easy))
        }
        Button(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            onClick = onStartNewMediumGame
        ) {
            Text(text = stringResource(id = R.string.difficulty_medium))
        }
        Button(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            onClick = onStartNewHardGame
        ) {
            Text(text = stringResource(id = R.string.difficulty_hard))
        }
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