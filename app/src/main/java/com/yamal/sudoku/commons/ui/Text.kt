package com.yamal.sudoku.commons.ui

import androidx.annotation.StringRes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp

@Composable
fun Title(
    modifier: Modifier = Modifier,
    @StringRes textResId: Int
) {
    Text(
        modifier = modifier,
        text = stringResource(id = textResId),
        fontSize = 32.sp
    )
}

@Composable
fun Paragraph(
    modifier:Modifier = Modifier,
    @StringRes textResId: Int
) {
    Text(
        modifier = modifier,
        text = stringResource(id = textResId),
    )
}
