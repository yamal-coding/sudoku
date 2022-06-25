package com.yamal.sudoku.game.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ThumbUp
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