package com.yamal.sudoku.game.status.data

import com.yamal.sudoku.commons.thread.ApplicationScope
import com.yamal.sudoku.storage.BoardStorage
import com.yamal.sudoku.test.base.CoroutinesUnitTest
import com.yamal.sudoku.test.utils.SudokuDOMother
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class GameStatusRepositoryTest : CoroutinesUnitTest() {

    private val storage: BoardStorage = mock()
    private val scope = ApplicationScope(dispatcherProvider)
    private val repository = GameStatusRepository(
        storage,
        scope,
        dispatcherProvider
    )

    @Test
    fun `Should return true when there is a saved board`() = runBlockingTest {
        givenASavedBoard()

        assertTrue(repository.hasSavedBoard())
    }

    @Test
    fun `Should return false when there is not a saved board`() = runBlockingTest {
        givenThereIsNotASavedBoard()

        assertFalse(repository.hasSavedBoard())
    }

    @Test
    fun `Should save board`() = runBlockingTest {
        val (givenBoard, expectedBoardToBeStored) = SudokuDOMother.someEasyBoardWithExpectedDOModel()

        repository.saveBoard(givenBoard)

        verify(storage).board = expectedBoardToBeStored
    }


    @Test
    fun `Should remove saved board`() = runBlockingTest {
        repository.removeSavedBoard()

        verify(storage).board = null
    }

    @Test
    fun `Should return true when show set up new game hint should be shown`() = runBlockingTest {
        whenShowNewGameHintShouldBeShown()

        assertTrue(repository.shouldShowSetUpNewGameHint())
    }

    @Test
    fun `Should return false when show set up new game hint should not be shown`() = runBlockingTest {
        whenShowNewGameHintShouldNotBeShown()

        assertFalse(repository.shouldShowSetUpNewGameHint())
    }

    private fun givenASavedBoard() {
        whenever(storage.board).thenReturn(SudokuDOMother.SOME_BOARD)
    }

    private fun givenThereIsNotASavedBoard() {
        whenever(storage.board).thenReturn(null)
    }

    private fun whenShowNewGameHintShouldBeShown() {
        whenever(storage.showSetUpNewGameHint).thenReturn(true)
    }

    private fun whenShowNewGameHintShouldNotBeShown() {
        whenever(storage.showSetUpNewGameHint).thenReturn(false)
    }
}