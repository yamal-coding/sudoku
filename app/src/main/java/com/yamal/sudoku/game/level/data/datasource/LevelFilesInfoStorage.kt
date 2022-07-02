package com.yamal.sudoku.game.level.data.datasource

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.yamal.sudoku.commons.storage.GlobalDataStorage
import com.yamal.sudoku.game.level.data.datasource.db.Level
import com.yamal.sudoku.game.level.data.datasource.db.LevelsDao
import com.yamal.sudoku.model.Difficulty
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

open class LevelFilesInfoStorage @Inject constructor(
    private val globalDataStorage: GlobalDataStorage,
    private val dao: LevelsDao,
) {
    open suspend fun getCurrentFileNumber(difficulty: Difficulty): Int =
        globalDataStorage.data.map { preferences ->
            preferences[getCurrentFileNumberKey(difficulty)]
        }.firstOrNull() ?: 1

    open suspend fun setCurrentFileNumber(difficulty: Difficulty, fileNumber: Int) {
        globalDataStorage.edit { preference ->
            preference[getCurrentFileNumberKey(difficulty)] = fileNumber
        }
    }

    private fun getCurrentFileNumberKey(difficulty: Difficulty): Preferences.Key<Int> =
        when (difficulty) {
            Difficulty.EASY -> EASY_CURRENT_FILE_NUMBER_KEY
            Difficulty.MEDIUM -> MEDIUM_CURRENT_FILE_NUMBER_KEY
            Difficulty.HARD -> HARD_CURRENT_FILE_NUMBER_KEY
        }

    open fun getAlreadyReturnedLevelsIndexesForGivenFile(fileName: String): Set<Int> =
        dao.getAlreadyReturnedLevelsIndexesForGivenFile(fileName).toSet()

    open fun markLevelAsAlreadyReturned(fileName: String, levelIndex: Int) {
        dao.markLevelAsAlreadyReturned(Level(levelsFile = fileName, index = levelIndex))
    }

    private companion object {
        val EASY_CURRENT_FILE_NUMBER_KEY = intPreferencesKey("easy_current_file_number")
        val MEDIUM_CURRENT_FILE_NUMBER_KEY = intPreferencesKey("medium_current_file_number")
        val HARD_CURRENT_FILE_NUMBER_KEY = intPreferencesKey("hard_current_file_number")
    }
}
