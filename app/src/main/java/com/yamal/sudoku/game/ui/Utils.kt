package com.yamal.sudoku.game.ui

import androidx.annotation.DrawableRes
import com.yamal.sudoku.R
import com.yamal.sudoku.model.SudokuCellValue

@DrawableRes
fun getSudokuCellIconOrNullIfEmpty(
    value: SudokuCellValue,
): Int? =
    when (value) {
        SudokuCellValue.EMPTY -> null
        SudokuCellValue.ONE -> R.drawable.ic_number_1
        SudokuCellValue.TWO -> R.drawable.ic_number_2
        SudokuCellValue.THREE -> R.drawable.ic_number_3
        SudokuCellValue.FOUR -> R.drawable.ic_number_4
        SudokuCellValue.FIVE -> R.drawable.ic_number_5
        SudokuCellValue.SIX -> R.drawable.ic_number_6
        SudokuCellValue.SEVEN -> R.drawable.ic_number_7
        SudokuCellValue.EIGHT -> R.drawable.ic_number_8
        SudokuCellValue.NINE -> R.drawable.ic_number_9
    }
