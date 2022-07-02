package com.yamal.sudoku.commons.ui.utils

import androidx.compose.ui.Modifier

fun Modifier.`if`(
    condition: Boolean,
    then: Modifier.() -> Modifier
): Modifier =
    if (condition) {
        then()
    } else {
        this
    }