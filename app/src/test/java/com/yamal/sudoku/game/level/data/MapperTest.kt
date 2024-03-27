package com.yamal.sudoku.game.level.data

import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.test.utils.AlmostSolvedSudokuMother
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.lang.StringBuilder

class MapperTest {
    @Test
    fun `Should return null when rawLevel length is lower than 81`() {
        assertNull(rawLevelToBoard("1", ANY_DIFFICULTY))
    }

    @Test
    fun `Should return null when rawLevel length is greater than 81`() {
        val builder = StringBuilder()
        repeat(82) {
            builder.append("1")
        }
        val rawLevel = builder.toString()
        assertNull(rawLevelToBoard(rawLevel, ANY_DIFFICULTY))
    }

    @Test
    fun `Should return null when rawLevel contains a character that is not a digit`() {
        val builder = StringBuilder()
        builder.append("a")
        repeat(81) {
            builder.append("1")
        }
        val rawLevel = builder.toString()
        assertNull(rawLevelToBoard(rawLevel, ANY_DIFFICULTY))
    }

    @Test
    fun `Should return Board given a valid rawLevel`() {
        assertEquals(
            AlmostSolvedSudokuMother.almostSolvedSudoku(),
            rawLevelToBoard(
                rawLevel = AlmostSolvedSudokuMother.almostSolvedSudokuAsRawLevel,
                difficulty = AlmostSolvedSudokuMother.almostSolvedSudoku().difficulty
            )
        )
    }

    private companion object {
        val ANY_DIFFICULTY = Difficulty.EASY
    }
}