package com.yamal.sudoku.commons.ui.utils

import androidx.compose.ui.Modifier

inline fun Modifier.modifyIf(
    condition: Boolean,
    then: Modifier.() -> Modifier
): Modifier =
    if (condition) {
        then()
    } else {
        this
    }