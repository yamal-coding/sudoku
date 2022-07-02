package com.yamal.sudoku.game.status.data.storage

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.yamal.sudoku.commons.json.JsonUtils
import com.yamal.sudoku.commons.storage.GlobalDataStorage
import com.yamal.sudoku.game.status.data.storage.model.BoardDO
import kotlinx.coroutines.flow.Flow
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
                preferences[BOARD_KEY_new] = jsonBoard
            } else {
                preferences.remove(BOARD_KEY_new)
            }
        }
    }

    open fun observeBoard(): Flow<BoardDO?> =
        dataStorage.data.map {
            it[BOARD_KEY_new]?.let { jsonBoard ->
                jsonUtils.fromJsonOrNull(jsonBoard, BoardDO::class.java)
            }
        }

    private companion object {
        const val BOARD_KEY = "board"
        val BOARD_KEY_new = stringPreferencesKey("board")
    }
}
