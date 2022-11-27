package com.yamal.sudoku.game.status.data

import com.yamal.sudoku.commons.thread.ApplicationScope
import com.yamal.sudoku.game.domain.Board
import com.yamal.sudoku.game.status.data.storage.GameStatusStorage
import com.yamal.sudoku.test.base.UnitTest
import com.yamal.sudoku.test.utils.SudokuDOMother
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
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
    fun `Should save board`() = runTest {
        val (givenBoard, expectedBoardToBeStored) = SudokuDOMother.someBoardWithExpectedDOModel()

        repository.saveBoard(givenBoard)

        verify(storage).updateBoard(expectedBoardToBeStored)
    }

    @Test
    fun `Should return null when there is no board`() = runTest {
        givenThereIsNotASavedBoard()

        assertNull(repository.getSavedBoard().firstOrNull())
    }

    @Test
    fun `Should return saved board`() = runTest {
        val expectedBoard = givenASavedBoard()

        assertEquals(expectedBoard, repository.getSavedBoard().firstOrNull())
    }

    @Test
    fun `Should remove saved board`() = runTest {
        repository.removeSavedBoard()

        verify(storage).updateBoard(null)
    }

    @Test
    fun `Should return saved time counter`() = runTest {
        val expectedTimeCounter = 1000L
        whenever(storage.getTimeCounter()).thenReturn(expectedTimeCounter)

        assertEquals(expectedTimeCounter, repository.getTimeCounterSync())
    }

    @Test
    fun `Should update time counter`() = runTest {
        val someTimeCounter = 1000L

        repository.saveTimeCounter(someTimeCounter)

        verify(storage).updateTimeCounter(someTimeCounter)
    }

    private fun givenASavedBoard(): Board {
        val (domainBoard, doBoard) = SudokuDOMother.someBoardWithExpectedDOModel()
        whenever(storage.observeBoard()).thenReturn(flowOf(doBoard))
        return domainBoard
    }

    private fun givenThereIsNotASavedBoard() {
        whenever(storage.observeBoard()).thenReturn(flowOf(null))
    }
}
