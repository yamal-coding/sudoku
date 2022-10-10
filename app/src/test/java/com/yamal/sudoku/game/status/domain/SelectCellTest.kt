package com.yamal.sudoku.game.status.domain

import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SelectCellTest {

    private val currentGame: CurrentGame = mock()
    private val selectCell = SelectCell(
        currentGame,
    )

    @Test
    fun `should select specified row and column when game has not finished`() {
        givenGameHasNotFinished()

        selectCell(selectedRow = ANY_ROW, selectedColumn = ANY_COLUMN)

        verify(currentGame).onSelectCell(selectedRow = ANY_ROW, selectedColumn = ANY_COLUMN)
    }

    @Test
    fun `should not select specified row and column when game has finished`() {
        givenGameHasFinished()

        selectCell(selectedRow = ANY_ROW, selectedColumn = ANY_COLUMN)

        verify(currentGame, never()).onSelectCell(any(), any())
    }

    private fun givenGameHasNotFinished() {
        whenever(currentGame.hasFinished()).thenReturn(false)
    }

    private fun givenGameHasFinished() {
        whenever(currentGame.hasFinished()).thenReturn(true)
    }

    private companion object {
        const val ANY_ROW = 1
        const val ANY_COLUMN = 2
    }
}