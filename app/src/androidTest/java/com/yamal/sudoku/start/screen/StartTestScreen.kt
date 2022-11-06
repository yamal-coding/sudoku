package com.yamal.sudoku.start.screen

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.performClick
import com.yamal.sudoku.R
import com.yamal.sudoku.game.screen.GameTestScreen
import com.yamal.sudoku.start.StartScreenTestTags
import com.yamal.sudoku.test.BaseScreen
import com.yamal.sudoku.test.waitForNodeAssertion

class StartTestScreen(
    private val composeTestRule: ComposeTestRule,
) : BaseScreen(composeTestRule, StartScreenTestTags.SCREEN) {
    fun continueButtonIsNotDisplayed(): StartTestScreen = apply {
        composeTestRule.waitForNodeAssertion(
            fetchNode = {
                it.onNodeWithTag(StartScreenTestTags.SCREEN)
            },
            assertion = {
                it.assert(hasAnyChild(hasText(context.getString(R.string.load_game_button))).not())
            }
        )
    }

    fun continueButtonIsDisplayed(): StartTestScreen = apply {
        composeTestRule.waitForNodeAssertion(
            fetchNode = {
                it.onNodeWithTag(StartScreenTestTags.CONTINUE_GAME_BUTTON)
            },
            assertion = {
                it.assertIsDisplayed()
            }
        )
    }

    fun clickOnContinueGame(): GameTestScreen {
        composeTestRule
            .onNodeWithTag(StartScreenTestTags.CONTINUE_GAME_BUTTON)
            .performClick()

        return GameTestScreen(composeTestRule)
    }
}
