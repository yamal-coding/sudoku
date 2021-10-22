package com.yamal.sudoku.game.level.data

import com.yamal.storage.KeyValueStorage
import com.yamal.sudoku.model.Difficulty
import javax.inject.Inject

open class LevelFilesInfoStorage @Inject constructor(
    private val keyValueStorage: KeyValueStorage
) {
    open fun getCurrentFileNumber(difficulty: Difficulty): Int =
        keyValueStorage.getInt(getCurrentFileNumberKey(difficulty), 0)

    open fun setCurrentFileNumber(difficulty: Difficulty, fileNumber: Int) {
        keyValueStorage.putInt(getCurrentFileNumberKey(difficulty), fileNumber)
    }

    private fun getCurrentFileNumberKey(difficulty: Difficulty): String =
        when (difficulty) {
            Difficulty.EASY -> EASY_CURRENT_FILE_NUMBER_KEY
            Difficulty.MEDIUM -> MEDIUM_CURRENT_FILE_NUMBER_KEY
            Difficulty.HARD -> HARD_CURRENT_FILE_NUMBER_KEY
        }

    private companion object {
        const val EASY_CURRENT_FILE_NUMBER_KEY = "easy_current_file_number"
        const val MEDIUM_CURRENT_FILE_NUMBER_KEY = "medium_current_file_number"
        const val HARD_CURRENT_FILE_NUMBER_KEY = "hard_current_file_number"
    }
}
