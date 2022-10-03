package com.yamal.sudoku.game.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yamal.sudoku.R
import com.yamal.sudoku.commons.ui.animation.AutomaticAnimatedVisibility
import com.yamal.sudoku.start.ui.MenuButton
import com.yamal.sudoku.start.ui.MenuDivider
import com.yamal.sudoku.start.ui.NewEasyGameButton
import com.yamal.sudoku.start.ui.NewHardGameButton
import com.yamal.sudoku.start.ui.NewMediumGameButton

@Composable
fun GameFinishedScreen(
    modifier: Modifier = Modifier,
    shouldShowNewGameButtons: Boolean,
    onPlayAgain: () -> Unit,
    onNewGame: (DifficultyViewData) -> Unit,
    onBackToMenu: () -> Unit,
) {
    AutomaticAnimatedVisibility(
        enter = fadeIn(animationSpec = tween(delayMillis = GameAnimationConstants.GameFinishedFadeInDelayDuration)),
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            VictoryHeader()
            Menu(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(fraction = 0.7F),
                shouldShowNewGameButtons = shouldShowNewGameButtons,
                onPlayAgain = onPlayAgain,
                onNewGame = onNewGame,
                onBackToMenu = onBackToMenu,
            )
        }
    }
}

@Composable
private fun VictoryHeader(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(108.dp),
            painter = painterResource(id = R.drawable.ic_check),
            contentDescription = null
        )
        Text(
            text = stringResource(id = R.string.game_finished_header_title),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        )
        Text(
            text = stringResource(id = R.string.game_finished_header_subtitle),
            fontWeight = FontWeight.Light,
            fontSize = 18.sp,
        )
    }
}

@Composable
private fun Menu(
    modifier: Modifier = Modifier,
    shouldShowNewGameButtons: Boolean,
    onPlayAgain: () -> Unit,
    onNewGame: (DifficultyViewData) -> Unit,
    onBackToMenu: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        MenuDivider()
        MenuButton(
            onClick = onPlayAgain,
            textRes = R.string.play_again_button,
            enabled = !shouldShowNewGameButtons
        )
        MenuDivider()

        AnimatedVisibility(visible = shouldShowNewGameButtons, enter = expandVertically()) {
            Column {
                NewEasyGameButton(onClick = onNewGame)
                MenuDivider()
                NewMediumGameButton(onClick = onNewGame)
                MenuDivider()
                NewHardGameButton(onClick = onNewGame)
                MenuDivider()
            }
        }

        MenuButton(
            onClick = onBackToMenu,
            textRes = R.string.back_to_menu_button,
            icon = R.drawable.ic_home,
        )
        MenuDivider()
    }
}
