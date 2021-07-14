package com.yamal.sudoku.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.yamal.sudoku.R
import com.yamal.sudoku.main.view.MainActivity
import com.yamal.sudoku.test.BaseTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.not
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class MainActivityTest : BaseTest<MainActivity>(MainActivity::class.java) {

    @Inject
    lateinit var mainScenario: MainScenario

    @Test
    fun continueGameButtonShouldNotBeDisplayedIfNoGameIsSaved() {
        mainScenario.givenNoSavedGame()

        givenThatCurrentScreenIsMain()

        onView(withId(R.id.load_saved_game_button)).check(matches(not(isDisplayed())))
    }

    @Test
    fun continueGameButtonShouldBeDisplayedIfThereIsASavedGame() {
        mainScenario.givenAnySavedGame()

        givenThatCurrentScreenIsMain()

        onView(withId(R.id.load_saved_game_button)).check(matches(isDisplayed()))
    }

    private fun givenThatCurrentScreenIsMain() {
        launchTarget()
    }
}
