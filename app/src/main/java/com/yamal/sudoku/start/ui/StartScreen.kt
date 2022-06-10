package com.yamal.sudoku.start.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yamal.sudoku.R
import com.yamal.sudoku.main.ui.viewmodel.MainComposeViewModel

@Suppress("UNUSED_PARAMETER")
@Composable
fun StartScreen(
    viewModel: MainComposeViewModel,
    onContinueGame: () -> Unit,
    onStartNewGame: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            onClick = onContinueGame
        ) {
            Text(text = stringResource(id = R.string.load_game_button))
        }
        Button(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            onClick = onStartNewGame
        ) {
            Text(text = stringResource(id = R.string.new_game_button))
        }
    }
}