package com.yamal.sudoku.game

import com.yamal.sudoku.game.scenario.GameScenario
import com.yamal.sudoku.main.view.MainActivity
import com.yamal.sudoku.test.BaseTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class SetUpGameTest : BaseTest<MainActivity>(MainActivity::class.java) {

    @Inject
    lateinit var gameScenario: GameScenario

    @Test
    fun hintDialogShouldBeDisplayedWhenSettingUpAGame() {
        givenThatCurrentScreenIsMainScreen()
            .clickOnSetUpGame()
            .expectHintDialog()
    }

    @Test
    fun boardShouldBeEmptyAndOnlyStartGameAndRemoveCellButtonsShouldBeDisplayedOnSetUpGameScreen() {
        gameScenario.givenThatSetUpNewGameHintWillNotBeDisplayed()

        givenThatCurrentScreenIsMainScreen()
            .clickOnSetUpGame()
            .expectEmptyBoard()
            .expectStartGameButton()
            .expectRemoveGameButton()
    }

    @Test
    fun hintDialogShouldNotBeDisplayedAgainIfUserMarkedDoNotShowAgain() {
        givenThatCurrentScreenIsMainScreen()
            .clickOnSetUpGame()
            .expectHintDialog()
            .markDoNotShowHintAgain()
            .dismissHintDialog()
            .goBack()
            .clickOnSetUpGame()
            .doNotExpectHintDialog()
    }

    @Test
    fun hintDialogShouldBeDisplayedAgainIfUserDidNotMarkedDoNotShowAgain() {
        givenThatCurrentScreenIsMainScreen()
            .clickOnSetUpGame()
            .expectHintDialog()
            .dismissHintDialog()
            .goBack()
            .clickOnSetUpGame()
            .expectHintDialog()
    }

    @Test
    fun aGameCanBeContinuedAfterSettingUpANewGame() {
        gameScenario.givenThatSetUpNewGameHintWillNotBeDisplayed()

        givenThatCurrentScreenIsMainScreen()
            .clickOnSetUpGame()
            .selectSomeCell()
            .setSomeCellValue()
            .expectBoardIs(GameScenario.SOME_BOARD)
            .clickOnFinishSetUp()
            .doNotExpectStartGameButton()
            .goBack()
            .clickOnContinueGame()
            .expectBoardIs(GameScenario.SOME_BOARD)
    }
}
