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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.yamal.sudoku.commons.ui.theme.SudokuTheme
import com.yamal.sudoku.model.SudokuCellValue

data class NumberViewData(
    val value: SudokuCellValue,
    val testTag: String
)

private val NUMBERS = listOf(
    NumberViewData(SudokuCellValue.ONE, GameTestTags.ONE_BUTTON),
    NumberViewData(SudokuCellValue.TWO, GameTestTags.TWO_BUTTON),
    NumberViewData(SudokuCellValue.THREE, GameTestTags.THREE_BUTTON),
    NumberViewData(SudokuCellValue.FOUR, GameTestTags.FOUR_BUTTON),
    NumberViewData(SudokuCellValue.FIVE, GameTestTags.FIVE_BUTTON),
    NumberViewData(SudokuCellValue.SIX, GameTestTags.SIX_BUTTON),
    NumberViewData(SudokuCellValue.SEVEN, GameTestTags.SEVEN_BUTTON),
    NumberViewData(SudokuCellValue.EIGHT, GameTestTags.EIGHT_BUTTON),
    NumberViewData(SudokuCellValue.NINE, GameTestTags.NINE_BUTTON),
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
                cellValue = number.value,
                testTag = number.testTag,
                onClick = onValueSelected
            )
        }
    }
}

@Suppress("MagicNumber")
@Composable
fun GridNumberPad(
    modifier: Modifier = Modifier,
    onValueSelected: (SudokuCellValue) -> Unit,
) {
    @Composable
    fun NumberRow(vararg numbers: NumberViewData) {
        Row(
            modifier = Modifier.wrapContentWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            numbers.forEach { number ->
                NumberButton(
                    cellValue = number.value,
                    testTag = number.testTag,
                    onClick = onValueSelected
                )
            }
        }
    }

    Column(
        modifier = modifier,
    ) {
        NumberRow(NUMBERS[0], NUMBERS[1], NUMBERS[2])
        NumberRow(NUMBERS[3], NUMBERS[4], NUMBERS[5])
        NumberRow(NUMBERS[6], NUMBERS[7], NUMBERS[8])
    }
}

@Composable
private fun NumberButton(
    modifier: Modifier = Modifier,
    cellValue: SudokuCellValue,
    testTag: String,
    onClick: (SudokuCellValue) -> Unit,
) {
    IconButton(
        modifier = modifier.testTag(testTag),
        onClick = { onClick(cellValue) }
    ) {
        getSudokuCellIconOrNullIfEmpty(cellValue)?.let {
            Icon(painter = painterResource(id = it), contentDescription = cellValue.toString())
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