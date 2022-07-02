package com.yamal.sudoku.game.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yamal.sudoku.commons.ui.theme.SudokuTheme
import com.yamal.sudoku.model.SudokuCellValue

@Composable
fun NumberPad(
    modifier: Modifier,
    onValueSelected: (SudokuCellValue) -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SudokuCellValue.values().forEach {
            NumberButton(
                cellValue = it,
                onClick = onValueSelected
            )
        }
    }
}

@Composable
private fun RowScope.NumberButton(
    cellValue: SudokuCellValue,
    onClick: (SudokuCellValue) -> Unit,
) {
    IconButton(
        modifier = Modifier.weight(1F),
        onClick = { onClick(cellValue) }
    ) {
        Icon(imageVector = getSudokuCellIcon(cellValue), contentDescription = null)
    }
}

@Preview
@Composable
private fun NumberPadPreview() {
    SudokuTheme {
        NumberPad(
            modifier = Modifier,
            onValueSelected = {}
        )
    }
}