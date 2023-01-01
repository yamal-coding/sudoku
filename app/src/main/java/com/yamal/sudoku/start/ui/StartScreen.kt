package com.yamal.sudoku.start.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.yamal.sudoku.R
import com.yamal.sudoku.commons.ui.IconButton
import com.yamal.sudoku.commons.ui.animation.AutomaticAnimatedVisibility
import com.yamal.sudoku.game.navigation.GameNavigationParams
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.start.StartScreenTestTags
import com.yamal.sudoku.start.ui.viewmodel.ExistingGameViewData
import com.yamal.sudoku.start.ui.viewmodel.StartScreenState
import com.yamal.sudoku.start.ui.viewmodel.StartViewModel

@Composable
fun StartScreen(
    viewModel: StartViewModel,
    onStartGame: (GameNavigationParams) -> Unit,
    onHowToPlayClicked: () -> Unit,
    onStatisticsClicked: () -> Unit,
) {
    val startScreenState by remember {
        viewModel.startScreenState
    }.collectAsState(initial = StartScreenState.Idle)

    when (startScreenState) {
        is StartScreenState.Idle -> {}
        is StartScreenState.Ready -> {
            StartScreen(
                existingGame = (startScreenState as StartScreenState.Ready).existingGame,
                onContinueGame = { existingGame ->
                    onStartGame(GameNavigationParams(existingGame.gameId, existingGame.difficulty))
                },
                onNewGame = { selectedDifficulty ->
                    onStartGame(GameNavigationParams(selectedDifficulty))
                },
                onHowToPlayClicked = onHowToPlayClicked,
                onStatisticsClicked = onStatisticsClicked,
            )
        }
    }
}

@Suppress("MagicNumber")
@Composable
private fun StartScreen(
    existingGame: ExistingGameViewData?,
    onContinueGame: (existingGame: ExistingGameViewData) -> Unit,
    onNewGame: (Difficulty) -> Unit,
    onHowToPlayClicked: () -> Unit,
    onStatisticsClicked: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .testTag(StartScreenTestTags.SCREEN),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .weight(1F),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            AnimatedHeader(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.7F)
                    .weight(0.4F)
            )
            Menu(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.7F)
                    .weight(0.6F),
                existingGame = existingGame,
                onContinueGame = onContinueGame,
                onNewGame = onNewGame,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            IconButton(
                onClick = onStatisticsClicked,
                iconRes = R.drawable.ic_stats,
                textRes = R.string.statistics_screen_title,
            )
            IconButton(
                onClick = onHowToPlayClicked,
                iconRes = R.drawable.ic_help,
                textRes = R.string.how_to_play_button,
            )
        }
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
        Header(modifier = modifier)
    }
}
