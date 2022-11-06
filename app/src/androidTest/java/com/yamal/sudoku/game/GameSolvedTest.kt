package com.yamal.sudoku.game

import com.yamal.sudoku.game.scenario.AlmostSolvedSudokuMother
import com.yamal.sudoku.game.scenario.GameScenario
import com.yamal.sudoku.test.BaseTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class GameSolvedTest : BaseTest() {

    @Inject
    lateinit var gameScenario: GameScenario

    @Test
    fun testGameShouldBeSolvedWhenEnteringLastNumber() {
        gameScenario.givenThereIsAnExistingGame(AlmostSolvedSudokuMother.someAlmostSolvedBoardDO())

        onStartScreen()
            .continueButtonIsDisplayed()
            .clickOnContinueGame()
            .selectCell(
                row = AlmostSolvedSudokuMother.getRemainingCellCoordinates().first,
                column = AlmostSolvedSudokuMother.getRemainingCellCoordinates().second
            )
            .setCellValue(
                value = AlmostSolvedSudokuMother.getRemainingCellValue()
            )
            .expectGameFinished()
    }

    @Test
    fun testGameShouldNotBeSolvedWhenEnteringLastButWrongNumber() {
        gameScenario.givenThereIsAnExistingGame(AlmostSolvedSudokuMother.someAlmostSolvedBoardDO())

        onStartScreen()
            .continueButtonIsDisplayed()
            .clickOnContinueGame()
            .selectCell(
                row = AlmostSolvedSudokuMother.getRemainingCellCoordinates().first,
                column = AlmostSolvedSudokuMother.getRemainingCellCoordinates().second
            )
            .setCellValue(
                value = AlmostSolvedSudokuMother.getWrongRemainingCellValue()
            )
            .expectGameNotFinished()
    }
}