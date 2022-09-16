package com.yamal.sudoku.start.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yamal.sudoku.commons.ui.animation.AutomaticAnimatedVisibility
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

@Suppress("MagicNumber")
@Composable
private fun StartScreen(
    shouldShowContinueButton: Boolean,
    onContinueGame: () -> Unit,
    onStartNewEasyGame: () -> Unit,
    onStartNewMediumGame: () -> Unit,
    onStartNewHardGame: () -> Unit,
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        AnimatedHeader(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4F)
        )
        Menu(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .weight(0.6F),
            shouldShowContinueButton = shouldShowContinueButton,
            onContinueGame = onContinueGame,
            onStartNewEasyGame = onStartNewEasyGame,
            onStartNewMediumGame = onStartNewMediumGame,
            onStartNewHardGame = onStartNewHardGame,
        )
    }
}

@Composable
private fun AnimatedHeader(
    modifier: Modifier = Modifier
) {
    AutomaticAnimatedVisibility(
        modifier = modifier,
        enter = fadeIn(animationSpec = tween(durationMillis = 700)),
    ) {
        Header()
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