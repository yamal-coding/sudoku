package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.game.status.data.GameStatusRepository
import com.yamal.sudoku.model.SudokuCellValue
import com.yamal.sudoku.stats.domain.UpdateGameFinishedStatistics
import com.yamal.sudoku.test.utils.AlmostSolvedSudokuMother
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class UpdateSelectedCellTest {
    private val currentGame: CurrentGame = mock()
    private val gameStatusRepository: GameStatusRepository = mock()
    private val timeCounter: TimeCounter = mock()
    private val updateStatistics: UpdateGameFinishedStatistics = mock()
    private val updateSelectedCell = UpdateSelectedCell(
        currentGame,
        gameStatusRepository,
        timeCounter,
        updateStatistics
    )

    @Before
    fun setUp() {
        givenAnyCurrentBoard()
    }

    @Test
    fun `should update selected cell when game has not finished`() {
        givenGameHasNotFinished()

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

    @Test
    fun `should remove saved board when game has finished`() {
        givenGameThatWillFinishOnLastUpdate()

        updateSelectedCell(ANY_CELL_VALUE)

        verify(gameStatusRepository).removeSavedBoard()
    }

    @Test
    fun `should stop time counter when game has finished`() {
        givenGameThatWillFinishOnLastUpdate()

        updateSelectedCell(ANY_CELL_VALUE)

        verify(timeCounter).stop()
    }

    @Test
    fun `game should be finished`() {
        givenGameThatWillFinishOnLastUpdate()

        updateSelectedCell(ANY_CELL_VALUE)

        verify(currentGame).onGameFinished()
    }

    @Test
    fun `Should update statistics when game has finished`() {
        givenGameThatWillFinishOnLastUpdate()
        givenSomeGameFinishedTimeInSeconds()

        updateSelectedCell(ANY_CELL_VALUE)

        verify(updateStatistics).invoke(ANY_BOARD.difficulty, ANY_GAME_TIME_IN_SECONDS)
    }

    private fun givenGameHasNotFinished() {
        whenever(currentGame.hasFinished()).thenReturn(false)
    }

    private fun givenGameHasFinished() {
        whenever(currentGame.hasFinished()).thenReturn(true)
    }

    private fun givenGameThatWillFinishOnLastUpdate() {
        whenever(currentGame.hasFinished())
            .thenReturn(false)
            .thenReturn(true)
    }

    private fun givenAnyCurrentBoard() {
        whenever(currentGame.getCurrentBoard()).thenReturn(ANY_BOARD)
    }

    private fun givenSomeGameFinishedTimeInSeconds() {
        whenever(timeCounter.getCurrentTime()).thenReturn(ANY_GAME_TIME_IN_SECONDS)
    }

    private companion object {
        val ANY_CELL_VALUE = SudokuCellValue.FIVE
        val ANY_BOARD = AlmostSolvedSudokuMother.almostSolvedSudoku()
        const val ANY_GAME_TIME_IN_SECONDS = 1L
    }
}