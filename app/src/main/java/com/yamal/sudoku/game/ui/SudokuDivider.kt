package com.yamal.sudoku.game.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yamal.sudoku.commons.ui.theme.SudokuTheme

@Composable
private fun SudokuDivider(
    modifier: Modifier = Modifier,
    type: SudokuDivider.Type,
    orientation: SudokuDivider.Orientation
) {
    Box(
        modifier = when (type) {
            SudokuDivider.Type.Light -> {
                modifier.orientationModifier(
                    orientation,
                    SudokuDivider.LIGHT_THICKNESS.dp
                )
            }
            SudokuDivider.Type.Strong -> {
                modifier.orientationModifier(
                    orientation,
                    SudokuDivider.STRONG_THICKNESS.dp
                )
            }
        }.background(SudokuTheme.colors.boardDivider)
    )
}

private fun Modifier.orientationModifier(
    orientation: SudokuDivider.Orientation,
    thickness: Dp
) =
    when (orientation) {
        SudokuDivider.Orientation.Vertical -> {
            fillMaxHeight().width(thickness)
        }
        SudokuDivider.Orientation.Horizontal -> {
            fillMaxWidth().height(thickness)
        }
    }

object SudokuDivider {
    enum class Orientation {
        Vertical, Horizontal
    }

    enum class Type {
        Light, Strong
    }

    const val LIGHT_THICKNESS = 1
    const val STRONG_THICKNESS = 2
}

@Composable
fun StrongHorizontalDivider(
    modifier: Modifier = Modifier,
) {
    SudokuDivider(
        modifier = modifier,
        type = SudokuDivider.Type.Strong,
        orientation = SudokuDivider.Orientation.Horizontal
    )
}

@Composable
fun LightHorizontalDivider(
    modifier: Modifier = Modifier,
) {
    SudokuDivider(
        modifier = modifier,
        type = SudokuDivider.Type.Light,
        orientation = SudokuDivider.Orientation.Horizontal
    )
}

@Composable
fun StrongVerticalDivider(
    modifier: Modifier = Modifier,
) {
    SudokuDivider(
        modifier = modifier,
        type = SudokuDivider.Type.Strong,
        orientation = SudokuDivider.Orientation.Vertical
    )
}

@Composable
fun LightVerticalDivider(
    modifier: Modifier = Modifier,
) {
    SudokuDivider(
        modifier = modifier,
        type = SudokuDivider.Type.Light,
        orientation = SudokuDivider.Orientation.Vertical
    )
}

@Preview
@Composable
private fun SudokuDividersPreview() {
    SudokuTheme {
        Column {
            StrongHorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
            LightHorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                StrongVerticalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                LightVerticalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
                    .fillMaxWidth()
                    .weight(1F)
            ) {
                StrongVerticalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                LightVerticalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}