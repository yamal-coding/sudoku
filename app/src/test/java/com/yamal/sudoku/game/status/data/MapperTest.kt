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
    fun `Should map to DO layer a domain layer board with null difficulty when difficulty is null`() {
        val (domainBoard, expectedDOBoard) = SudokuDOMother.someBoardWithoutDifficultyWithExpectedDOModelWithNullDifficulty()

        assertEquals(expectedDOBoard, domainBoard.toDO())
    }

    @Test
    fun `Should map to domain layer a DO layer board with easy difficulty`() {
        val (expectedDomainBoard, doBoard) = SudokuDOMother.someEasyBoardWithExpectedDOModel()

        assertEquals(expectedDomainBoard, doBoard.toDomain())
    }

    @Test
    fun `Should map to domain layer a DO layer board with medium difficulty`() {
        val (expectedDomainBoard, doBoard) = SudokuDOMother.someMediumBoardWithExpectedDOModel()

        assertEquals(expectedDomainBoard, doBoard.toDomain())
    }

    @Test
    fun `Should map to domain layer a DO layer board with hard difficulty`() {
        val (expectedDomainBoard, doBoard) = SudokuDOMother.someHardBoardWithExpectedDOModel()

        assertEquals(expectedDomainBoard, doBoard.toDomain())
    }

    @Test
    fun `Should map to domain layer with unknown difficulty a DO layer board with null difficulty`() {
        val (expectedDomainBoard, doBoard) = SudokuDOMother.someBoardWithoutDifficultyWithExpectedDOModelWithNullDifficulty()

        assertEquals(expectedDomainBoard, doBoard.toDomain())
    }

    @Test
    fun `Should map to domain layer with null difficulty a DO layer board with invalid difficulty`() {
        val (expectedDomainBoard, doBoard) = SudokuDOMother.someBoardWithoutDifficultyWithExpectedDOModelWithInvalidDifficulty()

        assertEquals(expectedDomainBoard, doBoard.toDomain())
    }
}