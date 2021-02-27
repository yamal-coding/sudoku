package com.yamal.sudoku.storage

import com.yamal.storage.KeyValueStorage
import com.yamal.sudoku.storage.model.BoardDO

class BoardStorage(
    private val keyValueStorage: KeyValueStorage
) {
    var board: BoardDO?
        get() = keyValueStorage.getJson(BOARD_KEY, BoardDO::class.java)
        set(value) {
            value?.let {
                keyValueStorage.putJson(BOARD_KEY, value, BoardDO::class.java)
            } ?: keyValueStorage.remove(BOARD_KEY)
        }

    var showSetUpNewGameHint: Boolean
        get() = keyValueStorage.getBoolean(SHOW_SET_UP_NEW_GAME_HINT, true)
        set(value) {
            keyValueStorage.put(SHOW_SET_UP_NEW_GAME_HINT, value)
        }

    private companion object {
        const val BOARD_KEY = "board"
        const val SHOW_SET_UP_NEW_GAME_HINT = "show_set_up_new_game_hint"
    }
}
