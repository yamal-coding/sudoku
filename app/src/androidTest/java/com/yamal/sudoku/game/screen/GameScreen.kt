package com.yamal.sudoku.game.screen

import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.yamal.sudoku.game.scenario.GameScenario.Companion.SOME_CELL_VALUE
import com.yamal.sudoku.game.scenario.GameScenario.Companion.SOME_COLUMN
import com.yamal.sudoku.game.scenario.GameScenario.Companion.SOME_REMAINING_CELL_VALE
import com.yamal.sudoku.game.scenario.GameScenario.Companion.SOME_ROW
import com.yamal.sudoku.game.scenario.GameScenario.Companion.COLUMN_OF_REMAINING_CELL
import com.yamal.sudoku.game.scenario.GameScenario.Companion.ROW_OF_REMAINING_CELL
import com.yamal.sudoku.R
import com.yamal.sudoku.main.MainScreen
import com.yamal.sudoku.model.Board
import com.yamal.sudoku.model.ReadOnlyBoard
import com.yamal.sudoku.model.SudokuCellValue
import com.yamal.sudoku.test.viewactions.SudokuBoardViewActions.selectCell
import com.yamal.sudoku.test.viewactions.SudokuBoardViewMatchers.contentIs
import org.hamcrest.CoreMatchers.not
import javax.inject.Inject
import javax.inject.Provider

class GameScreen @Inject constructor(
    private val mainScreenProvider: Provider<MainScreen>,
) {
    fun expectHintDialog(): GameScreen = apply {
        onView(withText(R.string.set_up_new_game_mode_hint_message)).check(matches(isDisplayed()))
    }

    fun expectEmptyBoard(): GameScreen = apply {
        onView(withId(R.id.sudoku_board)).check(matches(contentIs(Board.empty())))
    }

    fun expectStartGameButton(): GameScreen = apply {
        onView(withText(R.string.start_game)).check(matches(isDisplayed()))
    }

    fun expectRemoveGameButton(): GameScreen = apply {
        onView(withText(R.string.remove_button)).check(matches(isDisplayed()))
    }

    fun markDoNotShowHintAgain(): GameScreen = apply {
        onView(withId(R.id.do_not_ask_again_checkbox)).perform(click())
    }

    fun dismissHintDialog(): GameScreen = apply {
        onView(withText(R.string.set_up_new_game_mode_ok_button)).perform(click())
    }

    fun goBack(): MainScreen {
        pressBack()
        return mainScreenProvider.get()
    }

    fun doNotExpectHintDialog(): GameScreen = apply {
        onView(withText(R.string.set_up_new_game_mode_hint_message)).check(doesNotExist())
    }

    fun selectSomeCell(): GameScreen = apply {
        onView(withId(R.id.sudoku_board)).perform(selectCell(SOME_ROW, SOME_COLUMN))
    }

    fun setSomeCellValue(): GameScreen = apply {
        onView(withId(getButtonId(forCellValue = SOME_CELL_VALUE))).perform(click())
    }

    fun clickOnFinishSetUp(): GameScreen = apply {
        onView(withText(R.string.start_game)).perform(click())
    }

    fun doNotExpectStartGameButton(): GameScreen = apply {
        onView(withText(R.string.start_game)).check(matches(not(isDisplayed())))
    }

    fun expectBoardIs(board: ReadOnlyBoard): GameScreen = apply {
        onView(withId(R.id.sudoku_board)).check(matches(contentIs(board)))
    }

    fun selectRemainingCell(): GameScreen = apply {
        onView(withId(R.id.sudoku_board)).perform(selectCell(ROW_OF_REMAINING_CELL, COLUMN_OF_REMAINING_CELL))
    }

    fun setRemainingCell(): GameScreen = apply {
        onView(withId(getButtonId(forCellValue = SOME_REMAINING_CELL_VALE))).perform(click())
    }

    fun doNotExpectRemoveCellButton(): GameScreen = apply {
        onView(withText(R.string.remove_button)).check(matches(not(isDisplayed())))
    }

    fun doNotExpectNumberButtons(): GameScreen = apply {
        onView(withId(R.id.buttons_layout)).check(matches(not(isDisplayed())))
    }

    @IdRes
    private fun getButtonId(forCellValue: SudokuCellValue): Int =
        when(forCellValue) {
            SudokuCellValue.ONE -> R.id.one_button
            SudokuCellValue.TWO -> R.id.two_button
            SudokuCellValue.THREE -> R.id.three_button
            SudokuCellValue.FOUR -> R.id.four_button
            SudokuCellValue.FIVE -> R.id.five_button
            SudokuCellValue.SIX -> R.id.six_button
            SudokuCellValue.SEVEN -> R.id.seven_button
            SudokuCellValue.EIGHT -> R.id.eight_button
            SudokuCellValue.NINE -> R.id.nine_button
            SudokuCellValue.EMPTY -> R.id.remove_cell_button
        }
}
