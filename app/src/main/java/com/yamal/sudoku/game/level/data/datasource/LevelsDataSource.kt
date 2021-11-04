package com.yamal.sudoku.game.level.data.datasource

import android.content.Context
import com.yamal.sudoku.commons.utils.RandomGenerator
import com.yamal.sudoku.game.level.data.LevelDO
import com.yamal.sudoku.game.level.data.utils.LevelIdGenerator
import com.yamal.sudoku.game.level.data.utils.LevelsFileProvider
import com.yamal.sudoku.model.Difficulty
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface LevelsDataSource {
    fun getNewLevel(difficulty: Difficulty): LevelDO?
}

@Singleton
class LevelsDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val levelsFileProvider: LevelsFileProvider,
    private val randomGenerator: RandomGenerator,
    private val levelFilesInfoStorage: LevelFilesInfoStorage,
    private val levelIdGenerator: LevelIdGenerator
) : LevelsDataSource {

    override fun getNewLevel(difficulty: Difficulty): LevelDO? =
        getNewBoard(difficulty, levelFilesInfoStorage.getCurrentFileNumber(difficulty))

    private fun getNewBoard(difficulty: Difficulty, currentFileNumber: Int): LevelDO? {
        val candidateFileName = getFileName(difficulty, currentFileNumber)
        val candidateLevelsFile = levelsFileProvider.get(candidateFileName)

        return if (candidateLevelsFile.open(context)) {
            setCurrentFileNumberIfNeeded(difficulty, currentFileNumber)

            val alreadyReturnedLevelsIndexes = levelFilesInfoStorage.getAlreadyReturnedLevelsIndexesForGivenFile(candidateFileName)
            if (alreadyReturnedLevelsIndexes.size == candidateLevelsFile.getNumOfBoards()) {
                getNewBoard(difficulty, currentFileNumber + 1)
            } else {
                getRawLevel(candidateLevelsFile, alreadyReturnedLevelsIndexes)?.let {
                    val (levelIndex, rawBoard) = it
                    LevelDO(
                        id = levelIdGenerator.newId(candidateFileName, levelIndex),
                        difficulty = difficulty,
                        rawBoard = rawBoard
                    )
                }
            }.also {
                candidateLevelsFile.close()
            }
        } else {
            null
        }
    }

    private fun getRawLevel(file: LevelsFile, alreadyReturnedLevelsIndexesForGivenFile: Set<Int>): Pair<Int, String>? {
        fun getFirstGreaterIndex(candidateIndex: Int): Int? {
            var levelIndex: Int? = null
            for (i in (candidateIndex + 1) until file.getNumOfBoards()) {
                if (!alreadyReturnedLevelsIndexesForGivenFile.contains(i)) {
                    levelIndex = i
                    break
                }
            }
            return levelIndex
        }

        fun getFirstLowerIndex(candidateIndex: Int): Int? {
            var levelIndex: Int? = null
            for (i in (candidateIndex - 1) downTo 0) {
                if (!alreadyReturnedLevelsIndexesForGivenFile.contains(i)) {
                    levelIndex = i
                    break
                }
            }
            return levelIndex
        }

        val numOfBoardsForGivenFile = file.getNumOfBoards()
        val candidateLevelIndex = randomGenerator.randomInt(from = 0, until = numOfBoardsForGivenFile)

        return if (alreadyReturnedLevelsIndexesForGivenFile.contains(candidateLevelIndex)) {
            val levelIndex = getFirstGreaterIndex(candidateLevelIndex) ?: getFirstLowerIndex(candidateLevelIndex)
            levelIndex?.let { buildLevel(levelIndex, file.getRawLevel(it)) }
        } else {
            buildLevel(candidateLevelIndex, file.getRawLevel(candidateLevelIndex))
        }
    }

    private fun buildLevel(index: Int, rawLevel: String?): Pair<Int, String>? =
        rawLevel?.let { index to it }

    private fun getFileName(difficulty: Difficulty, fileNumber: Int): String {
        val filePrefix = when (difficulty) {
            Difficulty.EASY -> "easy"
            Difficulty.MEDIUM -> "medium"
            Difficulty.HARD -> "hard"
        }

        return "${filePrefix}_$fileNumber"
    }

    private fun setCurrentFileNumberIfNeeded(difficulty: Difficulty, fileNumber: Int) {
        if (levelFilesInfoStorage.getCurrentFileNumber(difficulty) < fileNumber) {
            levelFilesInfoStorage.setCurrentFileNumber(difficulty, fileNumber)
        }
    }
}
