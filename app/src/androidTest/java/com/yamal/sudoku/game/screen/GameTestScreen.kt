package com.yamal.sudoku.game.screen

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.yamal.sudoku.game.ui.GameTestTags
import com.yamal.sudoku.model.SudokuCellValue
import com.yamal.sudoku.test.BaseScreen

class GameTestScreen(
    private val composeTestRule: ComposeTestRule,
) : BaseScreen(composeTestRule, GameTestTags.SCREEN) {
    fun selectCell(row: Int, column: Int): GameTestScreen = apply {
        composeTestRule
            .onNodeWithTag(GameTestTags.cell(row = row, column = column))
            .performClick()
    }

    fun setCellValue(value: SudokuCellValue): GameTestScreen = apply {
        val valueTag = when (value) {
            SudokuCellValue.EMPTY -> GameTestTags.REMOVE_BUTTON
            SudokuCellValue.ONE -> GameTestTags.ONE_BUTTON
            SudokuCellValue.TWO -> GameTestTags.TWO_BUTTON
            SudokuCellValue.THREE -> GameTestTags.THREE_BUTTON
            SudokuCellValue.FOUR -> GameTestTags.FOUR_BUTTON
            SudokuCellValue.FIVE -> GameTestTags.FIVE_BUTTON
            SudokuCellValue.SIX -> GameTestTags.SIX_BUTTON
            SudokuCellValue.SEVEN -> GameTestTags.SEVEN_BUTTON
            SudokuCellValue.EIGHT -> GameTestTags.EIGHT_BUTTON
            SudokuCellValue.NINE -> GameTestTags.NINE_BUTTON
        }

        composeTestRule
            .onNodeWithTag(valueTag)
            .performClick()
    }

    fun expectGameFinished(): GameFinishedTestScreen =
        GameFinishedTestScreen(composeTestRule)

    fun expectGameNotFinished(): GameTestScreen = apply {
        onCurrentScreen()
    }
}