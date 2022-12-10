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
class IncreaseGamesWonTest : UnitTest() {

    private val repository: StatisticsRepository = mock()
    private val increaseGamesWon = IncreaseGamesWon(
        repository
    )

    @Test
    fun `Should increase by one previous number of games won`() = runTest {
        val somePreviousNumberOfGamesWon = 3L
        val someDifficulty = Difficulty.MEDIUM
        whenever(repository.getGamesWon(someDifficulty)).thenReturn(flowOf(somePreviousNumberOfGamesWon))

        increaseGamesWon(someDifficulty)

        verify(repository).setGamesWon(someDifficulty, somePreviousNumberOfGamesWon + 1)
    }

    @Test
    fun `Should set 1 as the number of games won is there isn't a previous value`() = runTest {
        val someDifficulty = Difficulty.MEDIUM
        whenever(repository.getGamesWon(someDifficulty)).thenReturn(flowOf(null))

        increaseGamesWon(someDifficulty)

        verify(repository).setGamesWon(someDifficulty, 1)
    }
}
