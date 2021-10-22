package com.yamal.sudoku.game.level.data

import com.yamal.sudoku.commons.utils.RandomGenerator
import com.yamal.sudoku.model.Board
import com.yamal.sudoku.model.Difficulty
import javax.inject.Inject
import javax.inject.Singleton

interface LevelsDataSource {
    fun getNewLevel(): Board?

    fun getNewLevel(difficulty: Difficulty): LevelDO?
}

@Singleton
class LevelsDataSourceImpl @Inject constructor(
    private val levelsFileProvider: LevelsFileProvider,
    private val randomGenerator: RandomGenerator,
    private val levelFilesInfoStorage: LevelFilesInfoStorage,
    private val levelIdGenerator: LevelIdGenerator
) : LevelsDataSource {
    override fun getNewLevel(): Board? =
        Board.almostDone()

    override fun getNewLevel(difficulty: Difficulty): LevelDO? =
        getNewBoard(difficulty, levelFilesInfoStorage.getCurrentFileNumber(difficulty))

    private fun getNewBoard(difficulty: Difficulty, currentFileNumber: Int): LevelDO? {
        val candidateFileName = getFileName(difficulty, currentFileNumber)
        val candidateLevelsFile = levelsFileProvider.get(candidateFileName)

        return if (candidateLevelsFile.open()) {
            setCurrentFileNumberIfNeeded(difficulty, currentFileNumber)

            val completedBoardsIndexes = getCompletedBoardIndexesForGivenFile(candidateFileName)
            if (completedBoardsIndexes.size == candidateLevelsFile.numOfBoards) {
                getNewBoard(difficulty, currentFileNumber + 1)
            } else {
                getRawLevel(candidateLevelsFile, completedBoardsIndexes)?.let {
                    val (levelIndex, rawBoard) = it
                    LevelDO(
                        id = levelIdGenerator.newId(candidateFileName, levelIndex),
                        difficulty = difficulty,
                        rawBoard = rawBoard
                    )
                }
            }
        } else {
            null
        }
    }

    private fun getRawLevel(file: LevelsFile, completedBoardsIndexesForGivenFile: Set<Int>): Pair<Int, String>? {
        fun getFirstGreaterIndex(candidateIndex: Int): Int? {
            var levelIndex: Int? = null
            for (i in (candidateIndex + 1) until file.numOfBoards) {
                if (!completedBoardsIndexesForGivenFile.contains(i)) {
                    levelIndex = i
                    break
                }
            }
            return levelIndex
        }

        fun getFirstLowerIndex(candidateIndex: Int): Int? {
            var levelIndex: Int? = null
            for (i in (candidateIndex - 1) downTo 0) {
                if (!completedBoardsIndexesForGivenFile.contains(i)) {
                    levelIndex = i
                }
            }
            return levelIndex
        }

        val numOfBoardsForGivenFile = file.numOfBoards
        val candidateLevelIndex = randomGenerator.randomInt(from = 0, until = numOfBoardsForGivenFile)

        return if (completedBoardsIndexesForGivenFile.contains(candidateLevelIndex)) {
            val levelIndex = getFirstGreaterIndex(candidateLevelIndex) ?: getFirstLowerIndex(candidateLevelIndex)
            levelIndex?.let { levelIndex to file.getRawLevel(it) }
        } else {
            candidateLevelIndex to file.getRawLevel(candidateLevelIndex)
        }
    }

    private fun getFileName(difficulty: Difficulty, fileNumber: Int): String {
        val filePrefix = when (difficulty) {
            Difficulty.EASY -> "easy"
            Difficulty.MEDIUM -> "medium"
            Difficulty.HARD -> "hard"
        }

        return "${filePrefix}_$fileNumber.txt"
    }

    private fun setCurrentFileNumberIfNeeded(difficulty: Difficulty, fileNumber: Int) {
        if (levelFilesInfoStorage.getCurrentFileNumber(difficulty) < fileNumber) {
            levelFilesInfoStorage.setCurrentFileNumber(difficulty, fileNumber)
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun getCompletedBoardIndexesForGivenFile(file: String): Set<Int> {
        TODO()
    }
}
