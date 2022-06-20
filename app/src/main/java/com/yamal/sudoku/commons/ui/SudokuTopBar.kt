package com.yamal.sudoku.commons.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable

@Composable
fun SudokuTopBar(
    title: String? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        navigationIcon = navigationIcon,
        title = {
            if (title != null) {
                Text(text = title)
            }
        },
        actions = actions,
    )
}

@Composable
fun BackButton(
    onBackClicked: () -> Unit,
) {
    IconButton(onClick = onBackClicked) {
        Icon(
            imageVector = Icons.Outlined.ArrowBack,
            contentDescription = null
        )
    }
}
