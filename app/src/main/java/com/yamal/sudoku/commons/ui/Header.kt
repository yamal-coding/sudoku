package com.yamal.sudoku.commons.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Header(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    @StringRes title: Int,
    @StringRes subtitle: Int,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(108.dp),
            painter = painterResource(id = icon),
            contentDescription = null
        )
        Text(
            text = stringResource(id = title),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        )
        Text(
            text = stringResource(id = subtitle),
            fontWeight = FontWeight.Light,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
        )
    }
}
