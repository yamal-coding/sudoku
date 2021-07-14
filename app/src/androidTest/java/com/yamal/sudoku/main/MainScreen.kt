package com.yamal.sudoku.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.yamal.sudoku.R
import com.yamal.sudoku.game.SetUpGameScreen
import javax.inject.Inject
import javax.inject.Provider

class MainScreen @Inject constructor(
    private val setUpGameScreenProvider: Provider<SetUpGameScreen>,
) {
    fun clickOnSetUpGame(): SetUpGameScreen {
        onView(withId(R.id.setup_new_game)).perform(click())
        return setUpGameScreenProvider.get()
    }
}