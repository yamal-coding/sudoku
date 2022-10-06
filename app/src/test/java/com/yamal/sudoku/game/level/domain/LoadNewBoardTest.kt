package com.yamal.sudoku.game.level.domain

import com.yamal.sudoku.game.level.data.LevelsRepository
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.test.base.UnitTest
import com.yamal.sudoku.test.utils.SolvedSudokuMother
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LoadNewBoardTest : UnitTest() {

    private val levelsRepository: LevelsRepository = mock()
    private val loadNewBoard = LoadNewBoard(
        levelsRepository = levelsRepository
    )

    @Test
    fun `should return level and mark it as already returned`() = runTest {
        givenALevel()

        val level = loadNewBoard(ANY_DIFFICULTY)

        assertEquals(SOME_LEVEL, level)
        verify(levelsRepository).markLevelAsAlreadyReturned(SOME_LEVEL.id)
    }

    @Test
    fun `should not mark level as already returned when no level was retrieved`() = runTest {
        givenThereIsNoLevel()

        val level = loadNewBoard(ANY_DIFFICULTY)

        assertNull(level)
        verify(levelsRepository, never()).markLevelAsAlreadyReturned(any())
    }

    private suspend fun givenALevel() {
        whenever(levelsRepository.getNewLevel(ANY_DIFFICULTY)).thenReturn(SOME_LEVEL)
    }

    private suspend fun givenThereIsNoLevel() {
        whenever(levelsRepository.getNewLevel(ANY_DIFFICULTY)).thenReturn(null)
    }

    private companion object {
        val ANY_DIFFICULTY = Difficulty.HARD

        val SOME_LEVEL = Level(
            id = "a",
            difficulty = ANY_DIFFICULTY,
            board = SolvedSudokuMother.solvedSudoku()
        )
    }
}