package com.yamal.sudoku.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.yamal.sudoku.R
import com.yamal.sudoku.game.screen.GameScreen
import org.hamcrest.CoreMatchers.not
import javax.inject.Inject
import javax.inject.Provider

class MainScreen @Inject constructor(
    private val gameScreenProvider: Provider<GameScreen>,
) {
    fun clickOnSetUpGame(): GameScreen {
        onView(withText(R.string.set_up_existing_game)).perform(click())
        return gameScreenProvider.get()
    }

    fun clickOnContinueGame(): GameScreen {
        onView(withText(R.string.load_game_button)).perform(click())
        return gameScreenProvider.get()
    }

    fun clickOnNewGame(): GameScreen {
        onView(withText(R.string.new_game_button)).perform(click())
        return gameScreenProvider.get()
    }

    fun expectContinueGameButton(): MainScreen = apply {
        onView(withId(R.id.load_saved_game_button)).check(matches(isDisplayed()))
    }

    fun doNotExpectContinueGameButton(): MainScreen = apply {
        onView(withId(R.id.load_saved_game_button)).check(matches(not(isDisplayed())))
    }
}
