package com.yamal.sudoku.game.level.data.datasource

import android.content.Context
import com.yamal.sudoku.commons.utils.RandomGenerator
import com.yamal.sudoku.game.level.data.LevelDO
import com.yamal.sudoku.game.level.data.utils.LevelIdGenerator
import com.yamal.sudoku.game.level.data.utils.LevelsFileProvider
import com.yamal.sudoku.model.Difficulty
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LevelsDataSourceImplTest {

    private val context: Context = mock()
    private val levelsFileProvider: LevelsFileProvider = mock()
    private val randomGenerator: RandomGenerator = mock()
    private val levelFilesInfoStorage: LevelFilesInfoStorage = mock()
    private val levelIdGenerator: LevelIdGenerator = LevelIdGenerator()

    private val dataSource = LevelsDataSourceImpl(
        context,
        levelsFileProvider,
        randomGenerator,
        levelFilesInfoStorage,
        levelIdGenerator
    )

    @Test
    fun `Get level given a difficulty picking file on first attempt`() {
        givenCurrentFileNumber(forGivenDifficulty = ANY_DIFFICULTY)
        givenARandomLevelIndex(from = 0, to = ANY_NUM_OF_LEVELS, returnIndex = ANY_LEVEL_INDEX)
        givenAFileThatWillOpen(fileName = ANY_FILE_NAME, withNumOfLevels = ANY_NUM_OF_LEVELS, thatWillOpen = true)
            .thenReturnRawLevel(ANY_LEVEL_INDEX, SOME_RAW_BOARD)
        givenCompletedLevelsIndexesForGivenFile(fileName = ANY_FILE_NAME, expected = emptySet())

        val level = dataSource.getNewLevel(ANY_DIFFICULTY)

        assertEquals(SOME_FILE_LEVEL, level)
    }

    @Test
    fun `Get level given a difficulty picking file on second attempt`() {
        val currentFileNumber = givenCurrentFileNumber(forGivenDifficulty = ANY_DIFFICULTY)
        givenAFileThatWillOpen(fileName = ANY_FILE_NAME, withNumOfLevels = ANY_NUM_OF_LEVELS, thatWillOpen = true)
        givenCompletedLevelsIndexesForGivenFile(fileName = ANY_FILE_NAME, expected = ALL_LEVEL_INDEXES)

        givenAFileThatWillOpen(fileName = ANY_NEXT_FILE_NAME, withNumOfLevels = ANY_NUM_OF_LEVELS, thatWillOpen = true)
            .thenReturnRawLevel(ANY_LEVEL_INDEX, SOME_RAW_BOARD)
        givenARandomLevelIndex(from = 0, to = ANY_NUM_OF_LEVELS, returnIndex = ANY_LEVEL_INDEX)
        givenCompletedLevelsIndexesForGivenFile(fileName = ANY_NEXT_FILE_NAME, expected = emptySet())

        val level = dataSource.getNewLevel(ANY_DIFFICULTY)

        assertEquals(SOME_NEXT_FILE_LEVEL, level)
        verify(levelFilesInfoStorage).setCurrentFileNumber(ANY_DIFFICULTY, currentFileNumber + 1)
    }

    @Test
    fun `Get first greater level after trying to pick a random level from file`() {
        givenCurrentFileNumber(forGivenDifficulty = ANY_DIFFICULTY)
        givenCompletedLevelsIndexesForGivenFile(fileName = ANY_FILE_NAME, expected = setOf(ANY_LEVEL_INDEX))
        givenARandomLevelIndex(from = 0, to = ANY_NUM_OF_LEVELS, returnIndex = ANY_LEVEL_INDEX)
        givenAFileThatWillOpen(fileName = ANY_FILE_NAME, withNumOfLevels = ANY_NUM_OF_LEVELS, thatWillOpen = true)
            .thenReturnRawLevel(ANY_HIGHER_LEVEL_INDEX, SOME_RAW_BOARD)

        val level = dataSource.getNewLevel(ANY_DIFFICULTY)

        assertEquals(SOME_FILE_HIGHER_LEVEL, level)
    }

    @Test
    fun `Get first lower level after trying to pick a random level from file`() {
        givenCurrentFileNumber(forGivenDifficulty = ANY_DIFFICULTY)
        givenCompletedLevelsIndexesForGivenFile(fileName = ANY_FILE_NAME, expected = setOf(ANY_LEVEL_INDEX, ANY_HIGHER_LEVEL_INDEX))
        givenARandomLevelIndex(from = 0, to = ANY_NUM_OF_LEVELS, returnIndex = ANY_LEVEL_INDEX)
        givenAFileThatWillOpen(fileName = ANY_FILE_NAME, withNumOfLevels = ANY_NUM_OF_LEVELS, thatWillOpen = true)
            .thenReturnRawLevel(ANY_LOWER_LEVEL_INDEX, SOME_RAW_BOARD)

        val level = dataSource.getNewLevel(ANY_DIFFICULTY)

        assertEquals(SOME_FILE_LOWER_LEVEL, level)
    }

    @Test
    fun `Trying to get level from file but every level file for given difficulty is completed`() {
        givenCurrentFileNumber(forGivenDifficulty = ANY_DIFFICULTY)
        givenCompletedLevelsIndexesForGivenFile(fileName = ANY_FILE_NAME, expected = ALL_LEVEL_INDEXES)
        givenARandomLevelIndex(from = 0, to = ANY_NUM_OF_LEVELS, returnIndex = ANY_LEVEL_INDEX)
        givenAFileThatWillOpen(fileName = ANY_FILE_NAME, withNumOfLevels = ANY_NUM_OF_LEVELS, thatWillOpen = false)

        val level = dataSource.getNewLevel(ANY_DIFFICULTY)

        assertEquals(null, level)
    }

    private fun givenCurrentFileNumber(forGivenDifficulty: Difficulty): Int = ANY_CURRENT_FILE_NUMBER.also {
        whenever(levelFilesInfoStorage.getCurrentFileNumber(forGivenDifficulty))
            .thenReturn(it)
    }

    private fun givenAFileThatWillOpen(fileName: String, withNumOfLevels: Int, thatWillOpen: Boolean): LevelsFile =
        mock<LevelsFile>().also { file ->
            whenever(file.getNumOfBoards()).thenReturn(withNumOfLevels)
            whenever(file.open(context)).thenReturn(thatWillOpen)
            whenever(levelsFileProvider.get(fileName)).thenReturn(file)
        }

    private fun LevelsFile.thenReturnRawLevel(forIndex: Int, returnBoard: String) {
        whenever(this.getRawLevel(forIndex)).thenReturn(returnBoard)
    }

    private fun givenCompletedLevelsIndexesForGivenFile(fileName: String, expected: Set<Int>) {
        whenever(levelFilesInfoStorage.getCompletedLevelsIndexesForGivenFile(fileName)).thenReturn(expected)
    }

    private fun givenARandomLevelIndex(from: Int, to: Int, returnIndex: Int) {
        whenever(randomGenerator.randomInt(from, to)).thenReturn(returnIndex)
    }

    private companion object {
        const val ANY_CURRENT_FILE_NUMBER = 2
        const val ANY_FILE_NAME = "easy_2"
        const val ANY_NEXT_FILE_NAME = "easy_3"
        const val ANY_LEVEL_INDEX = 3
        const val ANY_HIGHER_LEVEL_INDEX = 4
        const val ANY_LOWER_LEVEL_INDEX = 2
        const val ANY_NUM_OF_LEVELS = 5
        val ALL_LEVEL_INDEXES = setOf(1, 2, 3, 4, 5)
        const val SOME_RAW_BOARD = "200005040090004600407029350500402867720000090300900504070206035940081070000700901"
        val ANY_DIFFICULTY = Difficulty.EASY
        val SOME_FILE_LEVEL = LevelDO(
            id = "$ANY_FILE_NAME-$ANY_LEVEL_INDEX",
            difficulty = ANY_DIFFICULTY,
            rawBoard = SOME_RAW_BOARD
        )
        val SOME_FILE_HIGHER_LEVEL = LevelDO(
            id = "$ANY_FILE_NAME-$ANY_HIGHER_LEVEL_INDEX",
            difficulty = ANY_DIFFICULTY,
            rawBoard = SOME_RAW_BOARD
        )
        val SOME_FILE_LOWER_LEVEL = LevelDO(
            id = "$ANY_FILE_NAME-$ANY_LOWER_LEVEL_INDEX",
            difficulty = ANY_DIFFICULTY,
            rawBoard = SOME_RAW_BOARD
        )
        val SOME_NEXT_FILE_LEVEL = LevelDO(
            id = "$ANY_NEXT_FILE_NAME-$ANY_LEVEL_INDEX",
            difficulty = ANY_DIFFICULTY,
            rawBoard = SOME_RAW_BOARD
        )
    }
}
