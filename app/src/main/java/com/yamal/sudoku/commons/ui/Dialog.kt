package com.yamal.sudoku.commons.ui

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

@Composable
fun Dialog(
    title: String,
    subtitle: String? = null,
    onDismissRequest: () -> Unit,
    rightButtonText: String,
    onRightButtonClick: () -> Unit,
    leftButtonText: String? = null,
    onLeftButtonClick: (() -> Unit)?
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = title)
        },
        text = subtitle?.let {
            { Text(text = subtitle) }
        },
        confirmButton = {
            TextButton(onClick = onRightButtonClick) {
                Text(text = rightButtonText.uppercase())
            }
        },
        dismissButton = if (leftButtonText != null && onLeftButtonClick != null) {{
            TextButton(onClick = onLeftButtonClick) {
                Text(text = leftButtonText.uppercase())
            }
        }} else null
    )
}