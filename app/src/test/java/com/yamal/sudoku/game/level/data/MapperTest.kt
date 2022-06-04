package com.yamal.sudoku.game.level.data

import com.yamal.sudoku.test.utils.AlmostSolvedSudokuMother
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.lang.StringBuilder

class MapperTest {
    @Test
    fun `Should return null when rawLevel length is lower than 81`() {
        assertNull(rawLevelToBoard("1"))
    }

    @Test
    fun `Should return null when rawLevel length is greater than 81`() {
        val builder = StringBuilder()
        for (i in 0 until 82) {
            builder.append("1")
        }
        val rawLevel = builder.toString()
        assertNull(rawLevelToBoard(rawLevel))
    }

    @Test
    fun `Should return null when rawLevel contains a character that is not a digit`() {
        val builder = StringBuilder()
        builder.append("a")
        for (i in 0 until 80) {
            builder.append("1")
        }
        val rawLevel = builder.toString()
        assertNull(rawLevelToBoard(rawLevel))
    }

    @Test
    fun `Should return Board given a valid rawLevel`() {
        assertEquals(
            AlmostSolvedSudokuMother.almostSolvedSudoku(),
            rawLevelToBoard(AlmostSolvedSudokuMother.getAsRawLevel())
        )
    }
}