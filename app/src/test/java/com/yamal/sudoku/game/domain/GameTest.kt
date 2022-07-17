package com.yamal.sudoku.game.domain

import com.yamal.sudoku.commons.utils.getAsSudokuBoard
import com.yamal.sudoku.game.status.data.toSudokuCell
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.model.SudokuCell
import com.yamal.sudoku.model.SudokuCellValue
import com.yamal.sudoku.test.utils.AlmostSolvedSudokuMother
import com.yamal.sudoku.test.utils.SolvedSudokuMother
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class GameTest {

    @Test
    fun `Empty board is not solved`() {
        assertFalse(Game(empty(SOME_DIFFICULTY)).isSolved())
    }

    @Test
    fun `Board is solved when initialized as full board`() {
        assertTrue(
            Game(SolvedSudokuMother.solvedSudoku()).isSolved()
        )
    }

    @Test
    fun `Board is solved when initialized as empty and filled from the beginning`() {
        val cellValues = SolvedSudokuMother.solvedSudokuAsMap()

        val game = Game(empty(SOME_DIFFICULTY))
        for (x in 0..8) {
            for (y in 0..8) {
                game.selectCell(x, y)
                game.setSelectedCell( cellValues.getAsSudokuBoard(x, y).toSudokuCell())
            }
        }

        assertTrue(game.isSolved())
    }

    @Test
    fun `Not full board is not solved()`() {
        assertFalse(
            Game(AlmostSolvedSudokuMother.almostSolvedSudoku()).isSolved()
        )
    }

    @Test
    fun `Not full board is solved when completed()`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getEmptyCellCoordinates()
        game.selectCell(x, y)
        game.setSelectedCell(AlmostSolvedSudokuMother.getRemainingCellValue())

        assertTrue(game.isSolved())
    }

    @Test
    fun `Board with a number repeated in a quadrant is not solved`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getEmptyCellCoordinates()
        game.selectCell(x, y)
        game.setSelectedCell(AlmostSolvedSudokuMother.getWrongRemainingCellValue())

        assertFalse(game.isSolved())
    }

    @Test
    fun `Board is solved after fixing repeated number in a quadrant`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getEmptyCellCoordinates()
        game.selectCell(x, y)
        game.setSelectedCell(AlmostSolvedSudokuMother.getWrongRemainingCellValue())

        game.setSelectedCell(AlmostSolvedSudokuMother.getRemainingCellValue())

        assertTrue(game.isSolved())
    }

    @Test
    fun `should register movement`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getEmptyCellCoordinates()
        game.selectCell(x, y)
        game.setSelectedCell(AlmostSolvedSudokuMother.getWrongRemainingCellValue())

        assertTrue(game.canUndo)
    }

    @Test
    fun `should undo movement`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getEmptyCellCoordinates()
        game.selectCell(x, y)
        game.setSelectedCell(AlmostSolvedSudokuMother.getWrongRemainingCellValue())

        game.undo()
        assertEquals(game.currentBoard[x, y].value, SudokuCellValue.EMPTY)
    }

    @Test
    fun `should not register same movement twice`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getEmptyCellCoordinates()
        game.selectCell(x, y)
        game.setSelectedCell(AlmostSolvedSudokuMother.getWrongRemainingCellValue())

        game.setSelectedCell(AlmostSolvedSudokuMother.getWrongRemainingCellValue())

        game.undo()
        assertEquals(game.currentBoard[x, y].value, SudokuCellValue.EMPTY)
        assertFalse(game.canUndo)
    }

    @Test
    fun `should clear board resetting it to its initial state`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getEmptyCellCoordinates()
        game.selectCell(x, y)
        game.setSelectedCell(AlmostSolvedSudokuMother.getWrongRemainingCellValue())

        game.clear()
        assertEquals(game.currentBoard, AlmostSolvedSudokuMother.almostSolvedSudoku())
    }

    private fun empty(difficulty: Difficulty): Board =
        Board(
            mutableListOf<SudokuCell>().apply {
                repeat(BOARD_SIDE * BOARD_SIDE) {
                    add(
                        SudokuCell(
                            value = SudokuCellValue.EMPTY,
                            isFixed = false
                        )
                    )
                }
            },
            difficulty = difficulty
        )

    private companion object {
        val SOME_DIFFICULTY = Difficulty.EASY
    }
}
