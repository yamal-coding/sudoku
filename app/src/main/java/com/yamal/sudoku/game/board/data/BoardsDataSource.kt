package com.yamal.sudoku.game.board.data

import com.yamal.sudoku.commons.utils.RandomGenerator
import com.yamal.sudoku.model.Board
import com.yamal.sudoku.model.Difficulty
import javax.inject.Inject
import javax.inject.Singleton

interface BoardsDataSource {
    fun getNewBoard(): Board

    fun getNewBoard(difficulty: Difficulty): Board?
}

@Singleton
class BoardsDataSourceImpl @Inject constructor(
    private val levelsFileProvider: LevelsFileProvider,
    private val randomGenerator: RandomGenerator,
    private val levelFilesInfoStorage: LevelFilesInfoStorage
) : BoardsDataSource {
    override fun getNewBoard(): Board =
        Board.almostDone()

    override fun getNewBoard(difficulty: Difficulty): Board? =
        getNewBoard(difficulty, levelFilesInfoStorage.getCurrentFileNumber(difficulty))

    private fun getNewBoard(difficulty: Difficulty, currentFileNumber: Int): Board? {
        val candidateFileName = getFileName(difficulty, currentFileNumber)
        val candidateLevelsFile = levelsFileProvider.get(candidateFileName)

        return if (candidateLevelsFile.open()) {
            setCurrentFileNumberIfNeeded(difficulty, currentFileNumber)

            val completedBoardsIndexes = getCompletedBoardIndexesForGivenFile(candidateFileName)
            if (completedBoardsIndexes.size == candidateLevelsFile.numOfBoards) {
                getNewBoard(difficulty, currentFileNumber + 1)
            } else {
                getRawLevel(candidateLevelsFile, completedBoardsIndexes)?.let { rawLevel ->
                    rawLevelToDomain(rawLevel, difficulty)
                }
            }
        } else {
            null
        }
    }

    private fun getRawLevel(file: LevelsFile, completedBoardsIndexesForGivenFile: Set<Int>): String? {
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
            levelIndex?.let { file.getRawLevel(it) }
        } else {
            file.getRawLevel(candidateLevelIndex)
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
