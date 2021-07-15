package com.yamal.sudoku.game


import com.yamal.sudoku.game.scenario.GameScenario
import com.yamal.sudoku.main.view.MainActivity
import com.yamal.sudoku.test.BaseTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class NewGameTest : BaseTest<MainActivity>(MainActivity::class.java) {

    @Inject
    lateinit var gameScenario: GameScenario

    @Test
    fun startNewGameShouldDisplayContinueGameButtonOnMainScreen() {
        gameScenario.givenANewGame(withAGivenSudoku = GameScenario.SOME_ALMOST_FULL_BOARD)

        givenThatCurrentScreenIsMainScreen()
            .clickOnNewGame()
            .expectBoardIs(GameScenario.SOME_ALMOST_FULL_BOARD)
            .goBack()
            .clickOnContinueGame()
            .expectBoardIs(GameScenario.SOME_ALMOST_FULL_BOARD)

    }

    @Test
    fun startANewGameAndFinishItShouldHideRemoveAndNumberButtons() {
        gameScenario.givenANewGame(withAGivenSudoku = GameScenario.SOME_ALMOST_FULL_BOARD)

        givenThatCurrentScreenIsMainScreen()
            .clickOnNewGame()
            .expectBoardIs(GameScenario.SOME_ALMOST_FULL_BOARD)
            .selectRemainingCell()
            .setRemainingCell()
            .doNotExpectRemoveCellButton()
            .doNotExpectNumberButtons()
    }

    @Test
    fun startANewGameAndFinishItShouldHideContinueGameButtonOnMainScreen() {
        gameScenario.givenANewGame(withAGivenSudoku = GameScenario.SOME_ALMOST_FULL_BOARD)

        givenThatCurrentScreenIsMainScreen()
            .clickOnNewGame()
            .expectBoardIs(GameScenario.SOME_ALMOST_FULL_BOARD)
            .selectRemainingCell()
            .setRemainingCell()
            .goBack()
            .doNotExpectContinueGameButton()
    }

    @Test
    fun continueAGameAndFinishItShouldHideContinueGameButtonOnMainScreen() {
        gameScenario.givenASavedGame(withAGivenSudoku = GameScenario.SOME_ALMOST_FULL_BOARD)

        givenThatCurrentScreenIsMainScreen()
            .clickOnContinueGame()
            .expectBoardIs(GameScenario.SOME_ALMOST_FULL_BOARD)
            .selectRemainingCell()
            .setRemainingCell()
            .goBack()
            .doNotExpectContinueGameButton()
    }
}