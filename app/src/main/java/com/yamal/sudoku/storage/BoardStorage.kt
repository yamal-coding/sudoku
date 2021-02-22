package com.yamal.sudoku.storage

import android.content.SharedPreferences
import com.google.gson.Gson
import com.yamal.sudoku.storage.model.BoardDO

class BoardStorage(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences
) {
    var board: BoardDO?
        get() = sharedPreferences.getString(BOARD_KEY, null)?.let {
            gson.fromJson(it, BoardDO::class.java)
        }
        set(value) {
            if (value == null) {
                sharedPreferences.edit()
                    .remove(BOARD_KEY)
                    .apply()
            } else {
                val serializedBoard = gson.toJson(value, BoardDO::class.java)

                sharedPreferences.edit()
                    .putString(BOARD_KEY, serializedBoard)
                    .apply()
            }
        }

    var showSetUpNewGameHint: Boolean
        get() = sharedPreferences.getBoolean(SHOW_SET_UP_NEW_GAME_HINT, true)
        set(value) {
            sharedPreferences.edit()
                .putBoolean(SHOW_SET_UP_NEW_GAME_HINT, value)
                .apply()
        }

    private companion object {
        const val BOARD_KEY = "board"
        const val SHOW_SET_UP_NEW_GAME_HINT = "show_set_up_new_game_hint"
    }
}
