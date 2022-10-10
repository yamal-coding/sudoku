package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.game.status.data.GameStatusRepository
import com.yamal.sudoku.test.utils.AlmostSolvedSudokuMother
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class UndoTest {

    private val currentGame: CurrentGame = mock()
    private val gameStatusRepository: GameStatusRepository = mock()
    private val undo = Undo(
        currentGame,
        gameStatusRepository,
    )

    @Test
    fun `should undo movement when game has not finished`() {
        givenGameHasNotFinished()
        givenAnyCurrentBoard()

        undo()

        verify(currentGame).onUndo()
        verify(gameStatusRepository).saveBoard(ANY_BOARD)
    }

    @Test
    fun `should not undo movement when game has finished`() {
        givenGameHasFinished()

        undo()

        verify(currentGame, never()).onUndo()
        verify(gameStatusRepository, never()).saveBoard(any())
    }

    private fun givenGameHasNotFinished() {
        whenever(currentGame.hasFinished()).thenReturn(false)
    }

    private fun givenGameHasFinished() {
        whenever(currentGame.hasFinished()).thenReturn(true)
    }

    private fun givenAnyCurrentBoard() {
        whenever(currentGame.getCurrentBoard()).thenReturn(ANY_BOARD)
    }

    private companion object {
        val ANY_BOARD = AlmostSolvedSudokuMother.almostSolvedSudoku()
    }
}