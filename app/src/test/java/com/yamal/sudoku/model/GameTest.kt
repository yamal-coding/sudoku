package com.yamal.sudoku.model

import com.yamal.sudoku.commons.utils.getAsSudokuBoard
import com.yamal.sudoku.game.domain.Game
import com.yamal.sudoku.game.domain.Board
import com.yamal.sudoku.game.status.data.toSudokuCell
import com.yamal.sudoku.test.utils.AlmostSolvedSudokuMother
import com.yamal.sudoku.test.utils.SolvedSudokuMother
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class GameTest {

    @Test
    fun `Empty board is not solved`() {
        assertFalse(Game(Board.empty(SOME_DIFFICULTY)).isSolved())
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

        val game = Game(Board.empty(SOME_DIFFICULTY))
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

    private companion object {
        val SOME_DIFFICULTY = Difficulty.EASY
    }
}
