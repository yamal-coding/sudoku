package com.yamal.sudoku.stats.domain

import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.stats.data.StatisticsRepository
import com.yamal.sudoku.test.base.UnitTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class UpdateBestTimeTest : UnitTest() {

    private val repository: StatisticsRepository = mock()
    private val updateBestTime = UpdateBestTime(
        repository
    )

    @Test
    fun `should update best time`() = runTest {
        val someDifficulty = Difficulty.MEDIUM
        val someBestTime = 1L
        updateBestTime(someDifficulty, someBestTime)

        verify(repository).setBestTime(someDifficulty, someBestTime)
    }
}