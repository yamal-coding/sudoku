package com.yamal.sudoku.stats.domain

import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.stats.data.StatisticsRepository
import com.yamal.sudoku.test.base.UnitTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class UpdateGameFinishedStatisticsTest : UnitTest() {

    private val statisticsRepository: StatisticsRepository = mock()
    private val isNewBestTime: IsNewBestTime = mock()
    private val increaseGamesWon: IncreaseGamesWon = mock()
    private val updateGameFinishedStatistics = UpdateGameFinishedStatistics(
        statisticsRepository,
        isNewBestTime,
        increaseGamesWon,
    )

    @Test
    fun `Should update best time if new one is lower than previous`() = runTest {
        givenAnyExistingPlayedGamesStats()
        givenPreviousTimeIsStillBetter()

        updateGameFinishedStatistics(SOME_DIFFICULTY, SOME_GAME_TIME)

        verify(statisticsRepository).setBestTime(SOME_DIFFICULTY, SOME_GAME_TIME)
    }

    @Test
    fun `Should not update best time if new one is higher than previous one`() = runTest {
        givenAnyExistingPlayedGamesStats()
        givenNewTimeIsBetterThatPreviousOne()

        updateGameFinishedStatistics(SOME_DIFFICULTY, SOME_GAME_TIME)

        verify(statisticsRepository, never()).setBestTime(any(), any())
    }

    @Test
    fun `Should not update games played when there aren't any existing statistics`() = runTest {
        givenAnyExistingPlayedGamesStats()
        givenPreviousTimeIsStillBetter()

        updateGameFinishedStatistics(SOME_DIFFICULTY, SOME_GAME_TIME)

        verify(statisticsRepository, never()).setGamesPlayed(any(), any())
    }

    @Test
    fun `Should update games played when there aren't any existing statistics`() = runTest {
        givenThereArentAnyExistingGamesPlayedStats()
        givenPreviousTimeIsStillBetter()

        updateGameFinishedStatistics(SOME_DIFFICULTY, SOME_GAME_TIME)

        verify(statisticsRepository).setGamesPlayed(SOME_DIFFICULTY, 1)
    }

    @Test
    fun `Should increase games won`() = runTest {
        givenAnyExistingPlayedGamesStats()
        givenPreviousTimeIsStillBetter()

        updateGameFinishedStatistics(SOME_DIFFICULTY, SOME_GAME_TIME)

        increaseGamesWon(SOME_DIFFICULTY)
    }

    private suspend fun givenPreviousTimeIsStillBetter() {
        whenever(isNewBestTime(SOME_DIFFICULTY, SOME_GAME_TIME)).thenReturn(true)
    }

    private suspend fun givenNewTimeIsBetterThatPreviousOne() {
        whenever(isNewBestTime(SOME_DIFFICULTY, SOME_GAME_TIME)).thenReturn(false)
    }

    private fun givenAnyExistingPlayedGamesStats() {
        whenever(statisticsRepository.getGamesPlayed(SOME_DIFFICULTY)).thenReturn(flowOf(1L))
    }

    private fun givenThereArentAnyExistingGamesPlayedStats() {
        whenever(statisticsRepository.getGamesPlayed(SOME_DIFFICULTY)).thenReturn(flowOf(null))
    }

    private companion object {
        val SOME_DIFFICULTY = Difficulty.HARD

        const val SOME_GAME_TIME = 1L
    }
}