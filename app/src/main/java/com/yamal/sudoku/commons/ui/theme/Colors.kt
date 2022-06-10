package com.yamal.sudoku.commons.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

data class SudokuColors(
    val cellText: Color,
    val cellBackground: Color,
    val fixedCellBackground: Color,
)

val LightColors = SudokuColors(
    cellText = Color.Black,
    cellBackground = Color.White,
    fixedCellBackground = Color.LightGray,
)

val DarkColors = LightColors // TODO define dark colors

val LocalSudokuColors = compositionLocalOf {
    LightColors
}