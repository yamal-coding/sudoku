package com.yamal.sudoku.stats.data.storage

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.yamal.sudoku.commons.storage.GlobalDataStorage
import com.yamal.sudoku.model.Difficulty
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StatisticsStorage @Inject constructor(
    private val globalDataStorage: GlobalDataStorage
) {
    suspend fun setBestTime(difficulty: Difficulty, bestTimeInSeconds: Long) {
        globalDataStorage.edit { prefs ->
            prefs[buildBestTimeKey(difficulty)] = bestTimeInSeconds
        }
    }

    fun getBestTimeInSeconds(difficulty: Difficulty): Flow<Long?> =
        globalDataStorage.data.map { it[buildBestTimeKey(difficulty)] }

    suspend fun setGamesPlayed(difficulty: Difficulty, gamesPlayed: Long) {
        globalDataStorage.edit { prefs ->
            prefs[buildGamesPlayedKey(difficulty)] = gamesPlayed
        }
    }

    fun getGamesPlayed(difficulty: Difficulty): Flow<Long?> =
        globalDataStorage.data.map { it[buildGamesPlayedKey(difficulty)] }

    suspend fun setGamesWon(difficulty: Difficulty, gamesWon: Long) {
        globalDataStorage.edit { prefs ->
            prefs[buildGamesWonKey(difficulty)] = gamesWon
        }
    }

    fun getGamesWon(difficulty: Difficulty): Flow<Long?> =
        globalDataStorage.data.map { it[buildGamesWonKey(difficulty)] }

    private fun buildBestTimeKey(difficulty: Difficulty): Preferences.Key<Long> =
        longPreferencesKey("$BEST_TIME_KEY_PREFIX${getDifficultySuffix(difficulty)}")

    private fun buildGamesPlayedKey(difficulty: Difficulty): Preferences.Key<Long> =
        longPreferencesKey("$GAMES_PLAYED_KEY_PREFIX${getDifficultySuffix(difficulty)}")

    private fun buildGamesWonKey(difficulty: Difficulty): Preferences.Key<Long> =
        longPreferencesKey("$GAMES_WON_KEY_PREFIX${getDifficultySuffix(difficulty)}")

    private fun getDifficultySuffix(difficulty: Difficulty): String =
        when (difficulty) {
            Difficulty.EASY -> EASY_DIFFICULTY_SUFFIX
            Difficulty.MEDIUM -> MEDIUM_DIFFICULTY_SUFFIX
            Difficulty.HARD -> HARD_DIFFICULTY_SUFFIX
        }

    private companion object {
        const val BEST_TIME_KEY_PREFIX = "stats.best_time"
        const val GAMES_PLAYED_KEY_PREFIX = "stats.games_played"
        const val GAMES_WON_KEY_PREFIX = "stats.games_won"

        const val EASY_DIFFICULTY_SUFFIX = "_easy"
        const val MEDIUM_DIFFICULTY_SUFFIX = "_medium"
        const val HARD_DIFFICULTY_SUFFIX = "_hard"
    }
}
