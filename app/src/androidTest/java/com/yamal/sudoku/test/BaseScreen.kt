package com.yamal.sudoku.test

import android.content.Context
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.test.platform.app.InstrumentationRegistry

@Suppress("UnnecessaryAbstractClass")
abstract class BaseScreen(
    composeTestRule: ComposeTestRule,
    screenTestTag: String,
) {
    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    init {
        composeTestRule.waitForNodeWithTagDisplayed(screenTestTag)
    }
}