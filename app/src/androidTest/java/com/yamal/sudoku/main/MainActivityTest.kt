package com.yamal.sudoku.main

import com.yamal.sudoku.main.view.MainActivity
import com.yamal.sudoku.test.BaseTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class MainActivityTest : BaseTest<MainActivity>(MainActivity::class.java) {

    @Inject
    lateinit var mainScenario: MainScenario

    @Test
    fun continueGameButtonShouldNotBeDisplayedIfNoGameIsSaved() {
        mainScenario.givenNoSavedGame()

        givenThatCurrentScreenIsMainScreen()
            .doNotExpectContinueGameButton()
    }

    @Test
    fun continueGameButtonShouldBeDisplayedIfThereIsASavedGame() {
        mainScenario.givenAnySavedGame()

        givenThatCurrentScreenIsMainScreen()
            .expectContinueGameButton()
    }
}
