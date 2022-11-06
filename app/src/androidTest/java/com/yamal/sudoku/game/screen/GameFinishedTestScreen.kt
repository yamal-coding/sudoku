package com.yamal.sudoku.game.screen

import androidx.compose.ui.test.junit4.ComposeTestRule
import com.yamal.sudoku.game.ui.GameTestTags
import com.yamal.sudoku.test.BaseScreen

class GameFinishedTestScreen(
    private val composeTestRule: ComposeTestRule,
) : BaseScreen(composeTestRule, GameTestTags.FINISHED_SCREEN)