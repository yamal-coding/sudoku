package com.yamal.sudoku.start

import com.yamal.sudoku.game.scenario.GameScenario
import com.yamal.sudoku.test.BaseTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class StartScreenTest : BaseTest() {

    @Inject
    lateinit var gameScenario: GameScenario

    @Test
    fun startScreenShouldWithoutGameInProgress() {
        onStartScreen()
            .continueButtonIsNotDisplayed()
    }

    @Test
    fun startScreenShouldWithGameInProgress() {
        gameScenario.givenThereIsAnExistingGameInProgress()

        onStartScreen()
            .continueButtonIsDisplayed()
    }
}