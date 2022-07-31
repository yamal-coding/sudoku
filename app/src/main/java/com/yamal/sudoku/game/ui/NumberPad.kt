package com.yamal.sudoku.game.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.yamal.sudoku.commons.ui.theme.SudokuTheme
import com.yamal.sudoku.model.SudokuCellValue

private val NUMBERS = listOf(
    SudokuCellValue.ONE,
    SudokuCellValue.TWO,
    SudokuCellValue.THREE,
    SudokuCellValue.FOUR,
    SudokuCellValue.FIVE,
    SudokuCellValue.SIX,
    SudokuCellValue.SEVEN,
    SudokuCellValue.EIGHT,
    SudokuCellValue.NINE,
)

@Composable
fun RowNumberPad(
    modifier: Modifier,
    onValueSelected: (SudokuCellValue) -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NUMBERS.forEach { number ->
            NumberButton(
                modifier = Modifier.weight(1F),
                cellValue = number,
                onClick = onValueSelected
            )
        }
    }
}

@Composable
fun GridNumberPad(
    modifier: Modifier = Modifier,
    onValueSelected: (SudokuCellValue) -> Unit,
) {
    @Composable
    fun NumberRow(vararg numbers: SudokuCellValue) {
        Row(
            modifier = Modifier.wrapContentWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            numbers.forEach { number ->
                NumberButton(
                    cellValue = number,
                    onClick = onValueSelected
                )
            }
        }
    }

    Column(
        modifier = modifier,
    ) {
        NumberRow(SudokuCellValue.ONE, SudokuCellValue.TWO, SudokuCellValue.THREE)
        NumberRow(SudokuCellValue.FOUR, SudokuCellValue.FIVE, SudokuCellValue.SIX)
        NumberRow(SudokuCellValue.SEVEN, SudokuCellValue.EIGHT, SudokuCellValue.NINE)
    }
}

@Composable
private fun NumberButton(
    modifier: Modifier = Modifier,
    cellValue: SudokuCellValue,
    onClick: (SudokuCellValue) -> Unit,
) {
    IconButton(
        modifier = modifier,
        onClick = { onClick(cellValue) }
    ) {
        getSudokuCellIconOrNullIfEmpty(cellValue)?.let {
            Icon(painter = painterResource(id = it), contentDescription = null)
        }
    }
}

@Preview
@Composable
private fun NumberPadPreview() {
    SudokuTheme {
        RowNumberPad(
            modifier = Modifier,
            onValueSelected = {}
        )
    }
}