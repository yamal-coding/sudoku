package com.yamal.sudoku.start.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yamal.sudoku.R
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
    onHowToPlayClicked: () -> Unit,
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
                onStartNewHardGame = onStartNewHardGame,
                onHowToPlayClicked = onHowToPlayClicked,
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
    onHowToPlayClicked: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .weight(1F),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Header(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.7F)
                    .weight(0.4F)
            )
            Menu(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.7F)
                    .weight(0.6F),
                shouldShowContinueButton = shouldShowContinueButton,
                onContinueGame = onContinueGame,
                onStartNewEasyGame = onStartNewEasyGame,
                onStartNewMediumGame = onStartNewMediumGame,
                onStartNewHardGame = onStartNewHardGame,
            )
        }
        HowToPlayLink(
            modifier = Modifier.padding(vertical = 8.dp),
            onHowToPlayClicked = onHowToPlayClicked
        )
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier
) {
    AutomaticAnimatedVisibility(
        modifier = modifier,
        enter = fadeIn(animationSpec = tween(durationMillis = 700)),
    ) {
        Image(
            modifier = modifier,
            painter = painterResource(id = R.drawable.header),
            contentDescription = null
        )
    }
}

@Composable
private fun HowToPlayLink(
    modifier: Modifier = Modifier,
    onHowToPlayClicked: () -> Unit,
) {
    TextButton(
        modifier = modifier,
        onClick = onHowToPlayClicked
    ) {
        Text(text = stringResource(id = R.string.how_to_play_button))
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
            onStartNewHardGame = { },
            onHowToPlayClicked = { },
        )
    }
}