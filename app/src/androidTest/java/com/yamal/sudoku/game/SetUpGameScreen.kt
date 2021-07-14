package com.yamal.sudoku.game

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.yamal.sudoku.R
import com.yamal.sudoku.main.MainScreen
import javax.inject.Inject
import javax.inject.Provider

class SetUpGameScreen @Inject constructor(
    private val mainScreenProvider: Provider<MainScreen>,
) {
    fun expectHintDialog(): SetUpGameScreen = apply {
        onView(withText(R.string.set_up_new_game_mode_hint_message)).check(matches(isDisplayed()))
    }

    fun markDoNotShowHintAgain(): SetUpGameScreen = apply {
        onView(withId(R.id.do_not_ask_again_checkbox)).perform(click())
    }

    fun dismissHintDialog(): SetUpGameScreen = apply {
        onView(withText(R.string.set_up_new_game_mode_ok_button)).perform(click())
    }

    fun goBack(): MainScreen {
        pressBack()
        return mainScreenProvider.get()
    }

    fun doNotExpectHintDialog(): SetUpGameScreen = apply {
        onView(withText(R.string.set_up_new_game_mode_hint_message)).check(doesNotExist())
    }
}
