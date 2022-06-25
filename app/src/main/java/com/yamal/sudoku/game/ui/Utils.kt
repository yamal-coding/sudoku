package com.yamal.sudoku.game.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.yamal.sudoku.model.SudokuCellValue

// TODO use number image vectors instead of random icons
fun getSudokuCellIconOrNullIfEmpty(
    value: SudokuCellValue,
): ImageVector? =
    when (value) {
        SudokuCellValue.EMPTY -> null
        SudokuCellValue.ONE -> Icons.Default.Info
        SudokuCellValue.TWO -> Icons.Default.Add
        SudokuCellValue.THREE -> Icons.Default.ArrowBack
        SudokuCellValue.FOUR -> Icons.Default.ThumbUp
        SudokuCellValue.FIVE -> Icons.Default.Check
        SudokuCellValue.SIX -> Icons.Default.Call
        SudokuCellValue.SEVEN -> Icons.Default.AccountCircle
        SudokuCellValue.EIGHT -> Icons.Default.Lock
        SudokuCellValue.NINE -> Icons.Default.Email
    }

fun getSudokuCellIcon(
    value: SudokuCellValue,
): ImageVector =
    getSudokuCellIconOrNullIfEmpty(
        value
    ) ?: Icons.Default.Delete