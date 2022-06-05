package com.yamal.sudoku.game.status.data

import com.yamal.sudoku.commons.thread.ApplicationScope
import com.yamal.sudoku.game.domain.Board
import com.yamal.sudoku.game.status.data.storage.GameStatusStorage
import com.yamal.sudoku.test.base.UnitTest
import com.yamal.sudoku.test.utils.SudokuDOMother
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class GameStatusRepositoryTest : UnitTest() {

    private val storage: GameStatusStorage = mock()
    private val scope = ApplicationScope(testDispatcher)
    private val repository = GameStatusRepository(
        storage,
        scope,
        testDispatcher
    )

    @Test
    fun `Should return true when there is a saved board`() = runTest {
        givenASavedBoard()

        assertTrue(repository.hasSavedBoard())
    }

    @Test
    fun `Should return false when there is not a saved board`() = runTest {
        givenThereIsNotASavedBoard()

        assertFalse(repository.hasSavedBoard())
    }

    @Test
    fun `Should save board`() = runTest {
        val (givenBoard, expectedBoardToBeStored) = SudokuDOMother.someBoardWithExpectedDOModel()

        repository.saveBoard(givenBoard)

        verify(storage).board = expectedBoardToBeStored
    }

    @Test
    fun `Should return null when there is no board`() = runTest {
        givenThereIsNotASavedBoard()

        assertNull(repository.getSavedBoard())
    }

    @Test
    fun `Should return saved board`() = runTest {
        val expectedBoard = givenASavedBoard()

        assertEquals(expectedBoard, repository.getSavedBoard())
    }

    @Test
    fun `Should remove saved board`() = runTest {
        repository.removeSavedBoard()

        verify(storage).board = null
    }

    private fun givenASavedBoard(): Board {
        val (domainBoard, doBoard) = SudokuDOMother.someBoardWithExpectedDOModel()
        whenever(storage.board).thenReturn(doBoard)
        return domainBoard
    }

    private fun givenThereIsNotASavedBoard() {
        whenever(storage.board).thenReturn(null)
    }
}
