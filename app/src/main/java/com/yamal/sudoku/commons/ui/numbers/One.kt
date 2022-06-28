package com.yamal.sudoku.commons.ui.numbers

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val SudokuIcons.One: ImageVector
    get() {
        if (_one != null) {
            return _one!!
        }

        _one = materialIcon(name = "Sudoku.One") {
            materialPath {
                moveTo(10.50f, 0.0f)
                lineToRelative(-5.0f, 1.5f)
                verticalLineTo(4.5f)
                lineToRelative(5.0f, -1.5f)
                verticalLineTo(24.0f)
                horizontalLineTo(13.50f)
                verticalLineTo(0.0f)
                close()
            }
        }

        return _one!!
    }

private var _one: ImageVector? = null

@Composable
@Preview
private fun OnePreview() {
    Icon(
        modifier = Modifier.size(100.dp),
        imageVector = SudokuIcons.One,
        contentDescription = null
    )
}