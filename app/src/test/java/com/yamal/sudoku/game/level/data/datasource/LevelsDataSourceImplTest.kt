package com.yamal.sudoku.game.level.data.datasource

import android.content.Context
import com.yamal.sudoku.commons.utils.RandomGenerator
import com.yamal.sudoku.game.level.data.LevelDO
import com.yamal.sudoku.game.level.data.utils.LevelIdGenerator
import com.yamal.sudoku.game.level.data.utils.LevelsFileProvider
import com.yamal.sudoku.model.Difficulty
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
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
    fun `Get level given a difficulty picking file on first attempt`() = runTest {
        givenCurrentFileNumber(forGivenDifficulty = ANY_DIFFICULTY)
        givenARandomLevelIndex()
        givenAFileThatWillOpen(fileName = ANY_FILE_NAME, thatWillOpen = true)
            .thenReturnRawLevel(ANY_LEVEL_INDEX, SOME_RAW_BOARD)
        givenAlreadyReturnedLevelsIndexesForGivenFile(fileName = ANY_FILE_NAME, expected = emptySet())

        val level = dataSource.getNewLevel(ANY_DIFFICULTY)

        assertEquals(SOME_FILE_LEVEL, level)
        verify(levelFilesInfoStorage).markLevelAsAlreadyReturned(ANY_FILE_NAME, ANY_LEVEL_INDEX)
    }

    @Test
    fun `Get level given a difficulty picking file on second attempt`() = runTest {
        val currentFileNumber = givenCurrentFileNumber(forGivenDifficulty = ANY_DIFFICULTY)
        givenAFileThatWillOpen(fileName = ANY_FILE_NAME, thatWillOpen = true)
        givenAlreadyReturnedLevelsIndexesForGivenFile(fileName = ANY_FILE_NAME, expected = ALL_LEVEL_INDEXES)

        givenAFileThatWillOpen(fileName = ANY_NEXT_FILE_NAME, thatWillOpen = true)
            .thenReturnRawLevel(ANY_LEVEL_INDEX, SOME_RAW_BOARD)
        givenARandomLevelIndex()
        givenAlreadyReturnedLevelsIndexesForGivenFile(fileName = ANY_NEXT_FILE_NAME, expected = emptySet())

        val level = dataSource.getNewLevel(ANY_DIFFICULTY)

        assertEquals(SOME_NEXT_FILE_LEVEL, level)
        verify(levelFilesInfoStorage).setCurrentFileNumber(ANY_DIFFICULTY, currentFileNumber + 1)
        verify(levelFilesInfoStorage).markLevelAsAlreadyReturned(ANY_NEXT_FILE_NAME, ANY_LEVEL_INDEX)
    }

    @Test
    fun `Get first greater level after trying to pick a random level from file`() = runTest {
        givenCurrentFileNumber(forGivenDifficulty = ANY_DIFFICULTY)
        givenAlreadyReturnedLevelsIndexesForGivenFile(fileName = ANY_FILE_NAME, expected = setOf(ANY_LEVEL_INDEX))
        givenARandomLevelIndex()
        givenAFileThatWillOpen(fileName = ANY_FILE_NAME, thatWillOpen = true)
            .thenReturnRawLevel(ANY_HIGHER_LEVEL_INDEX, SOME_RAW_BOARD)

        val level = dataSource.getNewLevel(ANY_DIFFICULTY)

        assertEquals(SOME_FILE_HIGHER_LEVEL, level)
        verify(levelFilesInfoStorage).markLevelAsAlreadyReturned(ANY_FILE_NAME, ANY_HIGHER_LEVEL_INDEX)
    }

    @Test
    fun `Get first lower level after trying to pick a random level from file`() = runTest {
        givenCurrentFileNumber(forGivenDifficulty = ANY_DIFFICULTY)
        givenAlreadyReturnedLevelsIndexesForGivenFile(fileName = ANY_FILE_NAME, expected = setOf(ANY_LEVEL_INDEX, ANY_HIGHER_LEVEL_INDEX))
        givenARandomLevelIndex()
        givenAFileThatWillOpen(fileName = ANY_FILE_NAME, thatWillOpen = true)
            .thenReturnRawLevel(ANY_LOWER_LEVEL_INDEX, SOME_RAW_BOARD)

        val level = dataSource.getNewLevel(ANY_DIFFICULTY)

        assertEquals(SOME_FILE_LOWER_LEVEL, level)
        verify(levelFilesInfoStorage).markLevelAsAlreadyReturned(ANY_FILE_NAME, ANY_LOWER_LEVEL_INDEX)
    }

    @Test
    fun `Trying to get level from file but every level file for given difficulty is completed`() = runTest {
        givenCurrentFileNumber(forGivenDifficulty = ANY_DIFFICULTY)
        givenAlreadyReturnedLevelsIndexesForGivenFile(fileName = ANY_FILE_NAME, expected = ALL_LEVEL_INDEXES)
        givenARandomLevelIndex()
        givenAFileThatWillOpen(fileName = ANY_FILE_NAME, thatWillOpen = false)

        val level = dataSource.getNewLevel(ANY_DIFFICULTY)

        assertEquals(null, level)
    }

    private suspend fun givenCurrentFileNumber(forGivenDifficulty: Difficulty): Int = ANY_CURRENT_FILE_NUMBER.also {
        whenever(levelFilesInfoStorage.getCurrentFileNumber(forGivenDifficulty))
            .thenReturn(it)
    }

    private fun givenAFileThatWillOpen(fileName: String, thatWillOpen: Boolean): LevelsFile =
        mock<LevelsFile>().also { file ->
            whenever(file.getNumOfBoards()).thenReturn(ANY_NUM_OF_LEVELS)
            whenever(file.open(context)).thenReturn(thatWillOpen)
            whenever(levelsFileProvider.get(fileName)).thenReturn(file)
        }

    private fun LevelsFile.thenReturnRawLevel(forIndex: Int, returnBoard: String) {
        whenever(this.getRawLevel(forIndex)).thenReturn(returnBoard)
    }

    private fun givenAlreadyReturnedLevelsIndexesForGivenFile(fileName: String, expected: Set<Int>) {
        whenever(levelFilesInfoStorage.getAlreadyReturnedLevelsIndexesForGivenFile(fileName)).thenReturn(expected)
    }

    private fun givenARandomLevelIndex() {
        whenever(randomGenerator.randomInt(0, ANY_NUM_OF_LEVELS)).thenReturn(ANY_LEVEL_INDEX)
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
