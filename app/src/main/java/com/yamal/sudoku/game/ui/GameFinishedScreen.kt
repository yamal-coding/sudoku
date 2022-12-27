package com.yamal.sudoku.game.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.yamal.sudoku.R
import com.yamal.sudoku.commons.ui.Header
import com.yamal.sudoku.commons.ui.MenuButton
import com.yamal.sudoku.commons.ui.MenuDivider
import com.yamal.sudoku.game.navigation.GameNavigationParams
import com.yamal.sudoku.game.viewmodel.GameFinishedViewModel
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.start.ui.NewEasyGameButton
import com.yamal.sudoku.start.ui.NewHardGameButton
import com.yamal.sudoku.start.ui.NewMediumGameButton

@Composable
fun GameFinishedScreen(
    modifier: Modifier = Modifier,
    viewModel: GameFinishedViewModel,
    onNewGame: (GameNavigationParams) -> Unit,
    onBackToMenu: () -> Unit,
) {
    val shouldShowNewGameButtons by viewModel.shouldShowNewGameButtons.collectAsState(initial = false)
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .testTag(GameTestTags.FINISHED_SCREEN)
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
            onPlayAgain = {
                viewModel.onPLayAgain()
            },
            onNewGame = { selectedDifficulty ->
                onNewGame(GameNavigationParams(selectedDifficulty))
            },
            onBackToMenu = onBackToMenu,
        )
    }
}

@Composable
private fun VictoryHeader(
    modifier: Modifier = Modifier,
) {
    Header(
        modifier = modifier,
        icon = R.drawable.ic_check,
        title = R.string.game_finished_header_title,
        subtitle = R.string.game_finished_header_subtitle
    )
}

@Composable
private fun Menu(
    modifier: Modifier = Modifier,
    shouldShowNewGameButtons: Boolean,
    onPlayAgain: () -> Unit,
    onNewGame: (Difficulty) -> Unit,
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

        BackToMenuButton(
            onClick = onBackToMenu
        )
        MenuDivider()
    }
}
