package com.yamal.sudoku.test

import androidx.compose.ui.test.junit4.ComposeTestRule

@Suppress("UnnecessaryAbstractClass")
abstract class BaseScreen(
    composeTestRule: ComposeTestRule,
    screenTestTag: String,
) {
    init {
        composeTestRule.waitForNodeWithTagDisplayed(screenTestTag)
    }
}