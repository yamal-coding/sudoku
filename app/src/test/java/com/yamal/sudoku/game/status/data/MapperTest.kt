package com.yamal.sudoku.game.status.data

import com.yamal.sudoku.test.utils.SudokuDOMother
import org.junit.Assert.assertEquals
import org.junit.Test

class MapperTest {

    @Test
    fun `Should map to domain layer an stored board with easy difficulty`() {
        val (domainBoard, expectedDOBoard) = SudokuDOMother.someEasyBoardWithExpectedDOModel()

        assertEquals(expectedDOBoard, domainBoard.toDO())
    }

    @Test
    fun `Should map to domain layer an stored board with medium difficulty`() {
        val (domainBoard, expectedDOBoard) = SudokuDOMother.someMediumBoardWithExpectedDOModel()

        assertEquals(expectedDOBoard, domainBoard.toDO())
    }

    @Test
    fun `Should map to domain layer an stored board with hard difficulty`() {
        val (domainBoard, expectedDOBoard) = SudokuDOMother.someHardBoardWithExpectedDOModel()

        assertEquals(expectedDOBoard, domainBoard.toDO())
    }

    @Test
    fun `Should map to domain layer an stored board with null difficulty as unknown difficulty`() {
        val (domainBoard, expectedDOBoard) = SudokuDOMother.someUnknownBoardWithExpectedDOModelWithNullDifficulty()

        assertEquals(expectedDOBoard, domainBoard.toDO())
    }

    @Test
    fun `Should map to domain layer an stored board with invalid difficulty as unknown difficulty`() {
        val (domainBoard, expectedDOBoard) = SudokuDOMother.someUnknownBoardWithExpectedDOModelWithInvalidDifficulty()

        assertEquals(expectedDOBoard, domainBoard.toDO())
    }
}