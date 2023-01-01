package com.yamal.sudoku.commons.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @DrawableRes iconRes: Int,
    @StringRes textRes: Int,
    enabled: Boolean = true,
) {
    androidx.compose.material.IconButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val text = stringResource(id = textRes)
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = text
            )
            Text(text = text)
        }
    }
}