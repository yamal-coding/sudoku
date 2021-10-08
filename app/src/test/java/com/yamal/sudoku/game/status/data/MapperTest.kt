package com.yamal.sudoku.game.status.data

import com.yamal.sudoku.test.utils.SudokuDOMother
import org.junit.Assert.assertEquals
import org.junit.Test

class MapperTest {

    @Test
    fun `Should map to DO layer a domain layer board with easy difficulty`() {
        val (domainBoard, expectedDOBoard) = SudokuDOMother.someEasyBoardWithExpectedDOModel()

        assertEquals(expectedDOBoard, domainBoard.toDO())
    }

    @Test
    fun `Should map to DO layer a domain layer board with medium difficulty`() {
        val (domainBoard, expectedDOBoard) = SudokuDOMother.someMediumBoardWithExpectedDOModel()

        assertEquals(expectedDOBoard, domainBoard.toDO())
    }

    @Test
    fun `Should map to DO layer a domain layer board with hard difficulty`() {
        val (domainBoard, expectedDOBoard) = SudokuDOMother.someHardBoardWithExpectedDOModel()

        assertEquals(expectedDOBoard, domainBoard.toDO())
    }

    @Test
    fun `Should map to DO layer a domain layer board with null difficulty when difficulty is unknown`() {
        val (domainBoard, expectedDOBoard) = SudokuDOMother.someUnknownBoardWithExpectedDOModelWithNullDifficulty()

        assertEquals(expectedDOBoard, domainBoard.toDO())
    }
}