package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.commons.thread.ApplicationScope
import com.yamal.sudoku.game.status.data.GameStatusRepository
import com.yamal.sudoku.stats.domain.IsNewBestTime
import com.yamal.sudoku.stats.domain.UpdateGameFinishedStatistics
import com.yamal.sudoku.test.base.UnitTest
import com.yamal.sudoku.test.utils.AlmostSolvedSudokuMother
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class EndGameTest : UnitTest() {
    private val scope: ApplicationScope = ApplicationScope(testDispatcher)
    private val currentGame: CurrentGame = mock()
    private val repository: GameStatusRepository = mock()
    private val timeCounter: TimeCounter = mock()
    private val updateStatistics: UpdateGameFinishedStatistics = mock()
    private val isNewBestTime: IsNewBestTime = mock()

    private val endGame = EndGame(
        scope,
        currentGame,
        repository,
        timeCounter,
        updateStatistics,
        isNewBestTime,
    )

    @Before
    fun setUp() {
        givenAnyGameId()
        givenAnyCurrentBoard()
        givenSomeGameFinishedTimeInSeconds()
        runBlocking { givenThatGameTimeIsNotANewBestTime() }
    }

    @Test
    fun `Should stop timer`() = runTest {

        endGame()

        verify(timeCounter).stop()
    }

    @Test
    fun `Should update finished game summary with new best time`() = runTest {
        givenThatGameTimeIsANewBestTime()

        endGame()

        thenLastFinishedGameSummaryIsUpdated(isNewBestTime = true)
    }

    @Test
    fun `Should update finished game summary but not with a new best time`() = runTest {
        givenThatGameTimeIsNotANewBestTime()

        endGame()

        thenLastFinishedGameSummaryIsUpdated(isNewBestTime = false)
    }

    @Test
    fun `Should update statistics`() = runTest {
        endGame()

        verify(updateStatistics).invoke(ANY_BOARD.difficulty, ANY_GAME_TIME_IN_SECONDS)
    }

    @Test
    fun `Should notify that game has finished`() = runTest {
        endGame()

        verify(currentGame).onGameFinished()
    }

    @Test
    fun `should remove previous board`() = runTest {
        endGame()

        verify(repository).removeSavedBoard()
    }

    @Test
    fun `should remove previous game Id`() = runTest {
        endGame()

        verify(repository).removeGameId()
    }

    private fun givenAnyGameId() {
        whenever(currentGame.id).thenReturn(ANY_GAME_ID)
    }

    private fun givenAnyCurrentBoard() {
        whenever(currentGame.getCurrentBoard()).thenReturn(ANY_BOARD)
    }

    private fun givenSomeGameFinishedTimeInSeconds() {
        whenever(timeCounter.getCurrentTime()).thenReturn(ANY_GAME_TIME_IN_SECONDS)
    }

    private suspend fun givenThatGameTimeIsANewBestTime() {
        whenever(isNewBestTime(ANY_BOARD.difficulty, ANY_GAME_TIME_IN_SECONDS)).thenReturn(true)
    }

    private suspend fun givenThatGameTimeIsNotANewBestTime() {
        whenever(isNewBestTime(ANY_BOARD.difficulty, ANY_GAME_TIME_IN_SECONDS)).thenReturn(false)
    }

    private suspend fun thenLastFinishedGameSummaryIsUpdated(isNewBestTime: Boolean) {
        val expectedFinishedGameSummary = LastFinishedGameSummary(
            gameId = ANY_GAME_ID,
            isNewBestTime = isNewBestTime,
            gameTimeInSeconds = ANY_GAME_TIME_IN_SECONDS,
        )
        verify(repository).setLastFinishedGameSummary(expectedFinishedGameSummary)
    }

    private companion object {
        const val ANY_GAME_ID = "1234"
        val ANY_BOARD = AlmostSolvedSudokuMother.almostSolvedSudoku()
        const val ANY_GAME_TIME_IN_SECONDS = 1L
    }
}