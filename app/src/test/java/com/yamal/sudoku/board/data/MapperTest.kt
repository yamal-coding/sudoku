package com.yamal.sudoku.board.data

import com.yamal.sudoku.game.board.data.rawLevelToDomain
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.test.utils.AlmostSolvedSudokuMother
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.lang.StringBuilder

class MapperTest {
    @Test
    fun `Should return null when rawLevel length is lower than 81`() {
        assertNull(rawLevelToDomain("1", SOME_DIFFICULTY))
    }

    @Test
    fun `Should return null when rawLevel length is greater than 81`() {
        val builder = StringBuilder()
        for (i in 0 until 82) {
            builder.append("1")
        }
        val rawLevel = builder.toString()
        assertNull(rawLevelToDomain(rawLevel, SOME_DIFFICULTY))
    }

    @Test
    fun `Should return null when rawLevel contains a character that is not a digit`() {
        val builder = StringBuilder()
        builder.append("a")
        for (i in 0 until 80) {
            builder.append("1")
        }
        val rawLevel = builder.toString()
        assertNull(rawLevelToDomain(rawLevel, SOME_DIFFICULTY))
    }

    @Test
    fun `Should return Board given a valid rawLevel`() {
        assertEquals(
            AlmostSolvedSudokuMother.almostSolvedSudoku(),
            rawLevelToDomain(AlmostSolvedSudokuMother.getAsRawLevel(), AlmostSolvedSudokuMother.difficulty)
        )
    }

    private companion object {
        val SOME_DIFFICULTY = Difficulty.EASY
    }
}