package com.yamal.sudoku.game.screen

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.yamal.sudoku.game.scenario.SetUpGameScenario.Companion.SOME_COLUMN
import com.yamal.sudoku.game.scenario.SetUpGameScenario.Companion.SOME_ROW
import com.yamal.sudoku.R
import com.yamal.sudoku.main.MainScreen
import com.yamal.sudoku.model.Board
import com.yamal.sudoku.model.ReadOnlyBoard
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
        onView(withId(R.id.seven_button)).perform(click())
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
}
