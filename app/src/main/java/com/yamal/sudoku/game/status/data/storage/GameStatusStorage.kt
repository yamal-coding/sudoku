package com.yamal.sudoku.game.status.data.storage

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.yamal.sudoku.commons.json.JsonUtils
import com.yamal.sudoku.commons.storage.GlobalDataStorage
import com.yamal.sudoku.game.status.data.storage.model.BoardDO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

open class GameStatusStorage @Inject constructor(
    private val dataStorage: GlobalDataStorage,
    private val jsonUtils: JsonUtils,
) {

    open suspend fun updateBoard(boardDO: BoardDO?) {
        dataStorage.edit { preferences ->
            val jsonBoard = boardDO?.let {
                jsonUtils.toJson(it, BoardDO::class.java)
            }
            if (jsonBoard != null) {
                preferences[BOARD_KEY] = jsonBoard
            } else {
                preferences.remove(BOARD_KEY)
            }
        }
    }

    open fun observeBoard(): Flow<BoardDO?> =
        dataStorage.data.map {
            it[BOARD_KEY]?.let { jsonBoard ->
                jsonUtils.fromJsonOrNull(jsonBoard, BoardDO::class.java)
            }
        }

    open suspend fun updateTimeCounter(timeCounter: Long?) {
        dataStorage.edit {
            if (timeCounter != null) {
                it[TIME_COUNTER_KEY] = timeCounter
            } else {
                it.remove(TIME_COUNTER_KEY)
            }
        }
    }

    open suspend fun getTimeCounter(): Long? =
        dataStorage.data.firstOrNull()?.get(TIME_COUNTER_KEY)

    private companion object {
        val BOARD_KEY = stringPreferencesKey("board")
        val TIME_COUNTER_KEY = longPreferencesKey("time_counter")
    }
}
