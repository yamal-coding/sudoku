package com.yamal.sudoku.start.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yamal.sudoku.R
import com.yamal.sudoku.commons.ui.theme.SudokuTheme
import com.yamal.sudoku.game.ui.LightVerticalDivider
import com.yamal.sudoku.game.ui.SudokuDivider

@Composable
fun Header(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
    ) {
        SudokuedText(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun SudokuedText(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .border(
                width = SudokuDivider.STRONG_THICKNESS.dp,
                color = SudokuTheme.colors.boardDivider
            )
            .background(SudokuTheme.colors.cellBackground),
    ) {
        val textLength = SUDOKU_LETTERS_ICONS.size
        var i = 0

        while (i < textLength) {
            Letter(letterDrawableRes = SUDOKU_LETTERS_ICONS[i])
            if (i < textLength - 1) {
                LightVerticalDivider()
            }
            i++
        }
    }
}

@Composable
private fun RowScope.Letter(
    modifier: Modifier = Modifier,
    @DrawableRes letterDrawableRes: Int
) {
    Box(
        modifier = modifier
            .weight(1F)
            .aspectRatio(1F),
    ) {
        Icon(
            modifier = modifier
                .fillMaxSize(fraction = 0.4F)
                .align(Alignment.Center),
            painter = painterResource(id = letterDrawableRes),
            contentDescription = null
        )
    }
}

@Composable
@Preview
private fun SudokuHeaderPreview() {
    SudokuTheme {
        SudokuedText(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(20.dp),
        )
    }
}

private val SUDOKU_LETTERS_ICONS = listOf(
    R.drawable.ic_s,
    R.drawable.ic_u,
    R.drawable.ic_d,
    R.drawable.ic_o,
    R.drawable.ic_k,
    R.drawable.ic_u,
)