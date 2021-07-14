package com.yamal.sudoku.model

import com.yamal.sudoku.repository.toSudokuCell
import com.yamal.sudoku.test.utils.AlmostSolvedSudokuMother
import com.yamal.sudoku.test.utils.SolvedSudokuMother
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BoardTest {

    @Test
    fun `Empty board is not solved`() {
        assertFalse(Board.empty().isSolved())
    }

    @Test
    fun `Board is solved when initialized as full board`() {
        assertFalse(SolvedSudokuMother.solvedSudoku().isSolved())
    }

    @Test
    fun `Board is solved when initialized as empty and filled from the beginning`() {
        val cellValues = SolvedSudokuMother.solvedSudokuAsMap()

        val board = Board.empty()
        for (x in 0..8) {
            for (y in 0..8) {
                board.selectCell(x, y)
                board.setSelectedCell(cellValues[x][y].toSudokuCell())
            }
        }

        assertTrue(board.isSolved())
    }

    @Test
    fun `Not full board is not solved()`() {
        assertFalse(AlmostSolvedSudokuMother.almostSolvedSudoku().isSolved())
    }

    @Test
    fun `Not full board is solved when completed()`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()

        val (x, y) = AlmostSolvedSudokuMother.getEmptyCellCoordinates()
        almostDoneSudoku.selectCell(x, y)
        almostDoneSudoku.setSelectedCell(AlmostSolvedSudokuMother.getRemainingCellValue())

        assertTrue(almostDoneSudoku.isSolved())
    }

    @Test
    fun `Board with a number repeated in a quadrant is not solved`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()

        val (x, y) = AlmostSolvedSudokuMother.getEmptyCellCoordinates()
        almostDoneSudoku.selectCell(x, y)
        almostDoneSudoku.setSelectedCell(AlmostSolvedSudokuMother.getWrongRemainingCellValue())

        assertFalse(almostDoneSudoku.isSolved())
    }

    @Test
    fun `Board is solved after fixing repeated repeated number in a quadrant`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()

        val (x, y) = AlmostSolvedSudokuMother.getEmptyCellCoordinates()
        almostDoneSudoku.selectCell(x, y)
        almostDoneSudoku.setSelectedCell(AlmostSolvedSudokuMother.getWrongRemainingCellValue())

        almostDoneSudoku.setSelectedCell(AlmostSolvedSudokuMother.getRemainingCellValue())

        assertTrue(almostDoneSudoku.isSolved())
    }
}
