package com.yamal.sudoku.commons.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

data class SudokuColors(
    val textPrimary: Color,
    val cellText: Color,
    val cellBackground: Color,
    val fixedCellBackground: Color,
    val boardDivider: Color,
    val divider: Color,
    val selectedCellBackground: Color,
    val background: Color
) {
    companion object {
        val Black = Color(0xFF000000)
        val White = Color(0xFFFFFFFF)
        val Grey1 = Color(0xFF999999)
        val Grey2Alpha8 = Color(0x14212121)
        val Grey3 = Color(0xFFEEEEEE)
        val Grey4 = Color(0xFFDDDDDD)
        val Grey4Alpha20 = Color(0x33DDDDDD)
        val Grey5 = Color(0xFF242424)
        val Grey6 = Color(0xFF191919)
        val Yellow = Color(0xFFFFFF00)
        val YellowAlpha20 = Color(0x33FFFF00)
    }
}

val LightColors = SudokuColors(
    textPrimary = SudokuColors.Black,
    cellText = SudokuColors.Black,
    cellBackground = SudokuColors.White,
    fixedCellBackground = SudokuColors.Grey4,
    boardDivider = SudokuColors.Black,
    divider = SudokuColors.Grey2Alpha8,
    selectedCellBackground = SudokuColors.Yellow,
    background = SudokuColors.White,
)

val DarkColors = LightColors.copy(
    textPrimary = SudokuColors.Grey3,
    cellText = SudokuColors.Grey1,
    cellBackground = SudokuColors.Grey5,
    fixedCellBackground = SudokuColors.Grey4Alpha20,
    divider = SudokuColors.Grey1,
    selectedCellBackground = SudokuColors.YellowAlpha20,
    background = SudokuColors.Grey6
)

val LocalSudokuColors = compositionLocalOf {
    LightColors
}
