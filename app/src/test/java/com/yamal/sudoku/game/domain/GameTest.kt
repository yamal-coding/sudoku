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
import org.junit.Assert.assertNull
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

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.setSelectedCell(AlmostSolvedSudokuMother.getRemainingCellValue())

        assertTrue(game.isSolved())
    }

    @Test
    fun `Board with a number repeated in a quadrant is not solved`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.setSelectedCell(AlmostSolvedSudokuMother.getWrongRemainingCellValue())

        assertFalse(game.isSolved())
    }

    @Test
    fun `Board is solved after fixing repeated number in a quadrant`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.setSelectedCell(AlmostSolvedSudokuMother.getWrongRemainingCellValue())

        game.setSelectedCell(AlmostSolvedSudokuMother.getRemainingCellValue())

        assertTrue(game.isSolved())
    }

    @Test
    fun `should register movement`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.setSelectedCell(AlmostSolvedSudokuMother.getWrongRemainingCellValue())

        assertTrue(game.canUndo)
    }

    @Test
    fun `should undo movement`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.setSelectedCell(AlmostSolvedSudokuMother.getWrongRemainingCellValue())

        game.undo()
        assertEquals(game.currentBoard[x, y].value, SudokuCellValue.EMPTY)
    }

    @Test
    fun `should undo movement, select new value and then board should be solved`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.setSelectedCell(AlmostSolvedSudokuMother.getWrongRemainingCellValue())
        game.undo()
        game.setSelectedCell(AlmostSolvedSudokuMother.getRemainingCellValue())

        assertTrue(game.isSolved())
    }

    @Test
    fun `should set wrong cell value, remove it and then set correct value, and then game should be solved`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.setSelectedCell(AlmostSolvedSudokuMother.getWrongRemainingCellValue())
        game.setSelectedCell(SudokuCellValue.EMPTY)
        game.setSelectedCell(AlmostSolvedSudokuMother.getRemainingCellValue())

        assertTrue(game.isSolved())
    }

    @Test
    fun `should not register same movement twice`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.setSelectedCell(AlmostSolvedSudokuMother.getWrongRemainingCellValue())

        game.setSelectedCell(AlmostSolvedSudokuMother.getWrongRemainingCellValue())

        game.undo()
        assertEquals(game.currentBoard[x, y].value, SudokuCellValue.EMPTY)
        assertFalse(game.canUndo)
    }

    @Test
    fun `should not register removing empty cell value when it is the first movement`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.setSelectedCell(SudokuCellValue.EMPTY)

        assertFalse(game.canUndo)
    }

    @Test
    fun `should not register setting same value over an existing placed cell`() {
        val sameValue = AlmostSolvedSudokuMother.getWrongRemainingCellValue()
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku(
            remainingCelValue = sameValue
        )
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.setSelectedCell(sameValue)

        assertFalse(game.canUndo)
    }

    @Test
    fun `should register remove existing placed cell`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku(
            remainingCelValue = AlmostSolvedSudokuMother.getWrongRemainingCellValue()
        )
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.setSelectedCell(SudokuCellValue.EMPTY)

        assertTrue(game.canUndo)
    }

    @Test
    fun `should clear board resetting it to its initial state`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.setSelectedCell(AlmostSolvedSudokuMother.getWrongRemainingCellValue())

        game.clear()
        assertFalse(game.canUndo)
        assertEquals(game.currentBoard, AlmostSolvedSudokuMother.almostSolvedSudoku())
    }

    @Test
    fun `should clear board and then finishing game`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        game.clear()

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.setSelectedCell(AlmostSolvedSudokuMother.getRemainingCellValue())

        assertTrue(game.isSolved())
    }

    @Test
    fun `should add possibility to empty cell`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.addOrRemovePossibleValue(SOME_POSSIBILITY)

        assertEquals(setOf(SOME_POSSIBILITY), game.currentBoard[x, y].possibilities)
    }

    @Test
    fun `should undo adding possibility to empty cell`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.addOrRemovePossibleValue(SOME_POSSIBILITY)
        game.undo()

        assertEquals(game.currentBoard[x, y].value, SudokuCellValue.EMPTY)
    }

    @Test
    fun `should add possibility to non-empty cell`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.setSelectedCell(AlmostSolvedSudokuMother.getWrongRemainingCellValue())
        game.addOrRemovePossibleValue(SOME_POSSIBILITY)

        assertEquals(game.currentBoard[x, y].value, SudokuCellValue.EMPTY)
        assertEquals(setOf(SOME_POSSIBILITY), game.currentBoard[x, y].possibilities)
    }

    @Test
    fun `should undo adding possibility to non-empty cell`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.setSelectedCell(AlmostSolvedSudokuMother.getWrongRemainingCellValue())
        game.addOrRemovePossibleValue(SOME_POSSIBILITY)
        game.undo()

        assertEquals(
            game.currentBoard[x, y].value,
            AlmostSolvedSudokuMother.getWrongRemainingCellValue()
        )
        assertNull(game.currentBoard[x, y].possibilities)
    }

    @Test
    fun `should add possibility to existing possibilities set on cell`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.addOrRemovePossibleValue(SOME_POSSIBILITY)
        game.addOrRemovePossibleValue(SOME_OTHER_POSSIBILITY)

        assertEquals(
            setOf(SOME_POSSIBILITY, SOME_OTHER_POSSIBILITY),
            game.currentBoard[x, y].possibilities
        )
    }

    @Test
    fun `should undo adding possibility to existing possibilities set on cell`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.addOrRemovePossibleValue(SOME_POSSIBILITY)
        game.addOrRemovePossibleValue(SOME_OTHER_POSSIBILITY)
        game.undo()

        assertEquals(setOf(SOME_POSSIBILITY), game.currentBoard[x, y].possibilities)
    }

    @Test
    fun `should set value to existing possibilities set on cell`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.addOrRemovePossibleValue(SOME_POSSIBILITY)
        game.setSelectedCell(AlmostSolvedSudokuMother.getWrongRemainingCellValue())

        assertEquals(
            game.currentBoard[x, y].value,
            AlmostSolvedSudokuMother.getWrongRemainingCellValue()
        )
        assertNull(game.currentBoard[x, y].possibilities)
    }

    @Test
    fun `should undo setting value to existing possibilities set on cell`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.addOrRemovePossibleValue(SOME_POSSIBILITY)
        game.setSelectedCell(AlmostSolvedSudokuMother.getWrongRemainingCellValue())
        game.undo()

        assertEquals(game.currentBoard[x, y].value, SudokuCellValue.EMPTY)
        assertEquals(setOf(SOME_POSSIBILITY), game.currentBoard[x, y].possibilities)
    }

    @Test
    fun `should set value to existing possibilities set on cell and then board is solved`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.addOrRemovePossibleValue(SOME_POSSIBILITY)
        game.setSelectedCell(AlmostSolvedSudokuMother.getRemainingCellValue())

        assertTrue(game.isSolved())
    }

    @Test
    fun `should remove existing possibilities set on cell`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.addOrRemovePossibleValue(SOME_POSSIBILITY)
        game.setSelectedCell(SudokuCellValue.EMPTY)

        assertEquals(game.currentBoard[x, y].value, SudokuCellValue.EMPTY)
        assertNull(game.currentBoard[x, y].possibilities)
    }

    @Test
    fun `should undo removing existing possibilities set on cell`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.addOrRemovePossibleValue(SOME_POSSIBILITY)
        game.setSelectedCell(SudokuCellValue.EMPTY)
        game.undo()

        assertEquals(setOf(SOME_POSSIBILITY), game.currentBoard[x, y].possibilities)
    }

    @Test
    fun `should remove existing possibilities set on cell when possibility is empty`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.addOrRemovePossibleValue(SOME_POSSIBILITY)
        game.addOrRemovePossibleValue(SudokuCellValue.EMPTY)

        assertEquals(game.currentBoard[x, y].value, SudokuCellValue.EMPTY)
        assertNull(game.currentBoard[x, y].possibilities)
    }

    @Test
    fun `should undo removing existing possibilities set on cell when possibility is empty`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.addOrRemovePossibleValue(SOME_POSSIBILITY)
        game.addOrRemovePossibleValue(SudokuCellValue.EMPTY)
        game.undo()

        assertEquals(setOf(SOME_POSSIBILITY), game.currentBoard[x, y].possibilities)
    }

    @Test
    fun `should remove existing possibility when it is set again`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.addOrRemovePossibleValue(SOME_POSSIBILITY)
        game.addOrRemovePossibleValue(SOME_POSSIBILITY)

        assertEquals(emptySet<SudokuCellValue>(), game.currentBoard[x, y].possibilities)
    }

    @Test
    fun `should undo removing existing possibility when it is set again`() {
        val almostDoneSudoku = AlmostSolvedSudokuMother.almostSolvedSudoku()
        val game = Game(almostDoneSudoku)

        val (x, y) = AlmostSolvedSudokuMother.getRemainingCellCoordinates()
        game.selectCell(x, y)
        game.addOrRemovePossibleValue(SOME_POSSIBILITY)
        game.addOrRemovePossibleValue(SOME_POSSIBILITY)
        game.undo()

        assertEquals(setOf(SOME_POSSIBILITY), game.currentBoard[x, y].possibilities)
    }

    private fun empty(difficulty: Difficulty): Board =
        Board(
            mutableListOf<SudokuCell>().apply {
                repeat(BOARD_SIDE * BOARD_SIDE) {
                    add(
                        SudokuCell(
                            value = SudokuCellValue.EMPTY,
                            isFixed = false,
                            possibilities = null,
                        )
                    )
                }
            },
            difficulty = difficulty
        )

    private companion object {
        val SOME_DIFFICULTY = Difficulty.EASY
        val SOME_POSSIBILITY = SudokuCellValue.EIGHT
        val SOME_OTHER_POSSIBILITY = SudokuCellValue.NINE
    }
}
