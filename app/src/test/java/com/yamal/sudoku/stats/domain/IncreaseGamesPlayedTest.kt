package com.yamal.sudoku.stats.domain

import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.stats.data.StatisticsRepository
import com.yamal.sudoku.test.base.UnitTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class IncreaseGamesPlayedTest : UnitTest() {
    private val repository: StatisticsRepository = mock()
    private val increaseGamesPlayed = IncreaseGamesPlayed(
        repository
    )

    @Test
    fun `Should increase by one previous number of games played`() = runTest {
        val somePreviousNumberOfGamesPlayed = 3L
        val someDifficulty = Difficulty.MEDIUM
        whenever(repository.getGamesPlayed(someDifficulty)).thenReturn(flowOf(somePreviousNumberOfGamesPlayed))

        increaseGamesPlayed(someDifficulty)

        verify(repository).setGamesPlayed(someDifficulty, somePreviousNumberOfGamesPlayed + 1)
    }

    @Test
    fun `Should set 1 as the number of games played is there isn't a previous value`() = runTest {
        val someDifficulty = Difficulty.MEDIUM
        whenever(repository.getGamesPlayed(someDifficulty)).thenReturn(flowOf(null))

        increaseGamesPlayed(someDifficulty)

        verify(repository).setGamesPlayed(someDifficulty, 1)
    }
}