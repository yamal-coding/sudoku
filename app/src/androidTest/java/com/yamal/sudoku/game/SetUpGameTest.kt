package com.yamal.sudoku.game

import com.yamal.sudoku.game.scenario.SetUpGameScenario
import com.yamal.sudoku.main.MainScreen
import com.yamal.sudoku.main.view.MainActivity
import com.yamal.sudoku.test.BaseTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class SetUpGameTest : BaseTest<MainActivity>(MainActivity::class.java) {

    @Inject
    lateinit var mainScreen: MainScreen

    @Inject
    lateinit var setUpGameScenario: SetUpGameScenario

    @Test
    fun hintDialogShouldBeDisplayedWhenSettingUpAGame() {
        givenThatCurrentScreenIsMainScreen()
            .clickOnSetUpGame()
            .expectHintDialog()
    }

    @Test
    fun boardShouldBeEmptyAndOnlyStartGameAndRemoveCellButtonsShouldBeDisplayedOnSetUpGameScreen() {
        setUpGameScenario.givenThatSetUpNewGameHintWillNotBeDisplayed()

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
        setUpGameScenario.givenThatSetUpNewGameHintWillNotBeDisplayed()

        givenThatCurrentScreenIsMainScreen()
            .clickOnSetUpGame()
            .selectSomeCell()
            .setSomeCellValue()
            .expectBoardIs(SetUpGameScenario.SOME_BOARD)
            .clickOnFinishSetUp()
            .doNotExpectStartGameButton()
            .goBack()
            .clickOnContinueGame()
            .expectBoardIs(SetUpGameScenario.SOME_BOARD)
    }

    private fun givenThatCurrentScreenIsMainScreen(): MainScreen {
        launchTarget()
        return mainScreen
    }
}
