package com.yamal.sudoku.commons.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun SudokuTheme(
    isDarkThemeActive: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (isDarkThemeActive) DarkColors else LightColors

    CompositionLocalProvider(
        LocalSudokuColors provides colors
    ) {
        MaterialTheme(content = content)
    }
}

object SudokuTheme {
    val colors: SudokuColors
        @Composable
        @ReadOnlyComposable
        get() = LocalSudokuColors.current
}