package com.yamal.sudoku.stats.domain

import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.stats.data.StatisticsRepository
import com.yamal.sudoku.test.base.UnitTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class IsNewBestTimeTest : UnitTest() {
    private val repository: StatisticsRepository = mock()
    private val isNewBestTime = IsNewBestTime(
        repository
    )

    @Test
    fun `Should return true if there is no previous best time`() = runTest {
        givenAPreviousBestTimeInSeconds(null)

        assertTrue(isNewBestTime(SOME_DIFFICULTY, SOME_BEST_TIME_CANDIDATE))
    }

    @Test
    fun `Should return true if new time is lower than previous one`() = runTest {
        givenAPreviousBestTimeInSeconds(SOME_OLD_AND_HIGHER_BEST_TIME)

        assertTrue(isNewBestTime(SOME_DIFFICULTY, SOME_BEST_TIME_CANDIDATE))
    }

    @Test
    fun `Should return false if new time is equal to previous one`() = runTest {
        givenAPreviousBestTimeInSeconds(SOME_OLD_BUT_EQUAL_BEST_TIME)

        assertFalse(isNewBestTime(SOME_DIFFICULTY, SOME_BEST_TIME_CANDIDATE))
    }

    @Test
    fun `Should return false if new time is higher than previous one`() = runTest {
        givenAPreviousBestTimeInSeconds(SOME_OLD_BUT_LOWER_BEST_TIME)

        assertFalse(isNewBestTime(SOME_DIFFICULTY, SOME_BEST_TIME_CANDIDATE))
    }

    private fun givenAPreviousBestTimeInSeconds(previousTime: Long?) {
        whenever(repository.getBestTimeInSeconds(SOME_DIFFICULTY)).thenReturn(flowOf(previousTime))
    }
    private companion object {
        val SOME_DIFFICULTY = Difficulty.MEDIUM
        const val SOME_BEST_TIME_CANDIDATE = 1L
        const val SOME_OLD_AND_HIGHER_BEST_TIME = 2L
        const val SOME_OLD_BUT_LOWER_BEST_TIME = 0L
        const val SOME_OLD_BUT_EQUAL_BEST_TIME = SOME_BEST_TIME_CANDIDATE
    }
}