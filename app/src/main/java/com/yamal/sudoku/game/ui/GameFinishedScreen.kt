package com.yamal.sudoku.game.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yamal.sudoku.R
import com.yamal.sudoku.commons.ui.animation.AutomaticAnimatedVisibility
import com.yamal.sudoku.commons.ui.theme.SudokuTheme

@Composable
fun GameFinishedScreen() {
    AutomaticAnimatedVisibility(
        enter = fadeIn(animationSpec = tween(delayMillis = GameAnimationConstants.GameFinishedFadeInDelayDuration)),
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            VictoryHeader()
        }
    }
}

@Composable
private fun VictoryHeader(
    modifier: Modifier = Modifier
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

@Preview
@Composable
private fun GameFinishedPreview() {
    SudokuTheme {
        GameFinishedScreen()
    }
}