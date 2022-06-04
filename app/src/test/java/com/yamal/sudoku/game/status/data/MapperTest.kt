package com.yamal.sudoku.game.status.data

import com.yamal.sudoku.test.utils.SudokuDOMother
import org.junit.Assert.assertEquals
import org.junit.Test

class MapperTest {

    @Test
    fun `Should map to DO layer a domain layer board`() {
        val (domainBoard, expectedDOBoard) = SudokuDOMother.someBoardWithExpectedDOModel()

        assertEquals(expectedDOBoard, domainBoard.toDO())
    }

    @Test
    fun `Should map to domain layer a DO layer board`() {
        val (expectedDomainBoard, doBoard) = SudokuDOMother.someBoardWithExpectedDOModel()

        assertEquals(expectedDomainBoard, doBoard.toDomain())
    }
}