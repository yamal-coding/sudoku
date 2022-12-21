package com.yamal.sudoku.stats.domain

import com.yamal.sudoku.game.status.data.GameStatusRepository
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.stats.data.StatisticsRepository
import com.yamal.sudoku.test.base.UnitTest
import com.yamal.sudoku.test.utils.AlmostSolvedSudokuMother
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class GetGameStatisticsTest : UnitTest() {

    private val gameStatusRepository: GameStatusRepository = mock()
    private val statisticsRepository: StatisticsRepository = mock()
    private val getGameStatistics = GetGameStatistics(
        gameStatusRepository,
        statisticsRepository,
        testDispatcher,
    )

    @Test
    fun `Should return game statistics by difficulty`() = runTest {
        givenThereIsNoSavedBoard()
        givenSomeEasyGameStatistics()
        givenSomeMediumGameStatistics()
        givenSomeHardGameStatistics()

        val statsFlow = getGameStatistics()

        assertEquals(expectedGameStatisticsByDifficulty(), statsFlow.first())
    }

    @Test
    fun `Should return actual game statistics for easy difficulty when there are no previous stats but a game in progress`() = runTest {
        givenThereIsSomeSavedBoard(Difficulty.EASY)
        givenSomeEasyGameStatistics(gamesPlayed = null)
        givenSomeMediumGameStatistics()
        givenSomeHardGameStatistics()

        val statsFlow = getGameStatistics()

        assertEquals(expectedGameStatisticsByDifficulty(easyGamesPlayed = 1), statsFlow.first())
        verify(statisticsRepository).setGamesPlayed(Difficulty.EASY, 1)
    }

    @Test
    fun `Should return actual game statistics for easy difficulty when there are no previous stats neither a game in progress`() = runTest {
        givenThereIsNoSavedBoard()
        givenSomeEasyGameStatistics(gamesPlayed = null)
        givenSomeMediumGameStatistics()
        givenSomeHardGameStatistics()

        val statsFlow = getGameStatistics()

        assertEquals(expectedGameStatisticsByDifficulty(easyGamesPlayed = 0), statsFlow.first())
    }

    @Test
    fun `Should return actual game statistics for medium difficulty when there are no previous stats but a game in progress`() = runTest {
        givenThereIsSomeSavedBoard(Difficulty.MEDIUM)
        givenSomeEasyGameStatistics()
        givenSomeMediumGameStatistics(gamesPlayed = null)
        givenSomeHardGameStatistics()

        val statsFlow = getGameStatistics()

        assertEquals(expectedGameStatisticsByDifficulty(mediumGamesPlayed = 1), statsFlow.first())
        verify(statisticsRepository).setGamesPlayed(Difficulty.MEDIUM, 1)
    }

    @Test
    fun `Should return actual game statistics for medium difficulty when there are no previous stats neither a game in progress`() = runTest {
        givenThereIsNoSavedBoard()
        givenSomeEasyGameStatistics()
        givenSomeMediumGameStatistics(gamesPlayed = null)
        givenSomeHardGameStatistics()

        val statsFlow = getGameStatistics()

        assertEquals(expectedGameStatisticsByDifficulty(mediumGamesPlayed = 0), statsFlow.first())
    }

    @Test
    fun `Should return actual game statistics for hard difficulty when there are no previous stats but a game in progress`() = runTest {
        givenThereIsSomeSavedBoard(Difficulty.HARD)
        givenSomeEasyGameStatistics()
        givenSomeMediumGameStatistics()
        givenSomeHardGameStatistics(gamesPlayed = null)

        val statsFlow = getGameStatistics()

        assertEquals(expectedGameStatisticsByDifficulty(hardGamesPlayed = 1), statsFlow.first())
        verify(statisticsRepository).setGamesPlayed(Difficulty.HARD, 1)
    }

    @Test
    fun `Should return actual game statistics for hard difficulty when there are no previous stats neither a game in progress`() = runTest {
        givenThereIsNoSavedBoard()
        givenSomeEasyGameStatistics()
        givenSomeMediumGameStatistics()
        givenSomeHardGameStatistics(gamesPlayed = null)

        val statsFlow = getGameStatistics()

        assertEquals(expectedGameStatisticsByDifficulty(hardGamesPlayed = 0), statsFlow.first())
    }

    private fun givenThereIsNoSavedBoard() {
        whenever(gameStatusRepository.getSavedBoard()).thenReturn(flowOf(null))
    }

    private fun givenThereIsSomeSavedBoard(difficulty: Difficulty) {
        whenever(gameStatusRepository.getSavedBoard())
            .thenReturn(flowOf(AlmostSolvedSudokuMother.almostSolvedSudoku(difficulty = difficulty)))
    }

    private fun givenSomeEasyGameStatistics(gamesPlayed: Long? = SOME_EASY_GAMES_PLAYED) {
        givenSomeGameStatisticsByDifficulty(
            Difficulty.EASY,
            bestTimeInSeconds = SOME_BEST_EASY_TIME_IN_SECONDS,
            gamesPlayed = gamesPlayed,
            gamesWon = SOME_EASY_GAMES_WON,
        )
    }

    private fun givenSomeMediumGameStatistics(gamesPlayed: Long? = SOME_MEDIUM_GAMES_PLAYED) {
        givenSomeGameStatisticsByDifficulty(
            Difficulty.MEDIUM,
            bestTimeInSeconds = SOME_BEST_MEDIUM_TIME_IN_SECONDS,
            gamesPlayed = gamesPlayed,
            gamesWon = SOME_MEDIUM_GAMES_WON,
        )
    }

    private fun givenSomeHardGameStatistics(gamesPlayed: Long? = SOME_HARD_GAMES_PLAYED) {
        givenSomeGameStatisticsByDifficulty(
            Difficulty.HARD,
            bestTimeInSeconds = SOME_BEST_HARD_TIME_IN_SECONDS,
            gamesPlayed = gamesPlayed,
            gamesWon = SOME_HARD_GAMES_WON,
        )
    }

    private fun givenSomeGameStatisticsByDifficulty(
        difficulty: Difficulty,
        bestTimeInSeconds: Long,
        gamesPlayed: Long?,
        gamesWon: Long
    ) {
        whenever(statisticsRepository.getBestTimeInSeconds(difficulty)).thenReturn(flowOf(bestTimeInSeconds))
        whenever(statisticsRepository.getGamesPlayed(difficulty)).thenReturn(flowOf(gamesPlayed))
        whenever(statisticsRepository.getGamesWon(difficulty)).thenReturn(flowOf(gamesWon))
    }

    private fun expectedGameStatisticsByDifficulty(
        easyGamesPlayed: Long = SOME_EASY_GAMES_PLAYED,
        mediumGamesPlayed: Long = SOME_MEDIUM_GAMES_PLAYED,
        hardGamesPlayed: Long = SOME_HARD_GAMES_PLAYED,
    ) =
        GameStatisticsByDifficulty(
            easyStatistics = GameStatistics(
                bestTimeInSeconds = SOME_BEST_EASY_TIME_IN_SECONDS,
                gamesPlayed = easyGamesPlayed,
                gamesWon = SOME_EASY_GAMES_WON,
            ),
            mediumStatistics = GameStatistics(
                bestTimeInSeconds = SOME_BEST_MEDIUM_TIME_IN_SECONDS,
                gamesPlayed = mediumGamesPlayed,
                gamesWon = SOME_MEDIUM_GAMES_WON,
            ),
            hardStatistics = GameStatistics(
                bestTimeInSeconds = SOME_BEST_HARD_TIME_IN_SECONDS,
                gamesPlayed = hardGamesPlayed,
                gamesWon = SOME_HARD_GAMES_WON,
            ),
        )

    private companion object {
        const val SOME_BEST_EASY_TIME_IN_SECONDS = 1L
        const val SOME_BEST_MEDIUM_TIME_IN_SECONDS = 2L
        const val SOME_BEST_HARD_TIME_IN_SECONDS = 3L

        const val SOME_EASY_GAMES_PLAYED = 4L
        const val SOME_MEDIUM_GAMES_PLAYED = 5L
        const val SOME_HARD_GAMES_PLAYED = 6L

        const val SOME_EASY_GAMES_WON = 7L
        const val SOME_MEDIUM_GAMES_WON = 8L
        const val SOME_HARD_GAMES_WON = 9L
    }
}
