package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.game.status.data.GameStatusRepository
import com.yamal.sudoku.model.SudokuCellValue
import com.yamal.sudoku.test.utils.AlmostSolvedSudokuMother
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class UpdateSelectedCellTest {
    private val currentGame: CurrentGame = mock()
    private val gameStatusRepository: GameStatusRepository = mock()
    private val updateSelectedCell = UpdateSelectedCell(
        currentGame,
        gameStatusRepository,
    )

    @Test
    fun `should update selected cell when game has not finished`() {
        givenGameHasNotFinished()
        givenAnyCurrentBoard()

        updateSelectedCell(ANY_CELL_VALUE)

        verify(currentGame).onCellUpdated(ANY_CELL_VALUE)
        verify(gameStatusRepository).saveBoard(ANY_BOARD)
    }

    @Test
    fun `should not update selected cell when game has finished`() {
        givenGameHasFinished()

        updateSelectedCell(ANY_CELL_VALUE)

        verify(currentGame, never()).onCellUpdated(any())
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
        val ANY_CELL_VALUE = SudokuCellValue.FIVE
        val ANY_BOARD = AlmostSolvedSudokuMother.almostSolvedSudoku()
    }
}