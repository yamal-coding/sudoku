package com.yamal.sudoku.storage

import android.content.SharedPreferences
import com.google.gson.Gson
import com.yamal.sudoku.storage.model.BoardDO

class BoardStorage(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences
) {
    fun setBoard(board: BoardDO) {
        val serializedBoard = gson.toJson(board, BoardDO::class.java)

        sharedPreferences.edit()
            .putString(BOARD_KEY, serializedBoard)
            .apply()
    }

    fun getBoard(): BoardDO? =
        sharedPreferences.getString(BOARD_KEY, null)?.let {
            gson.fromJson(it, BoardDO::class.java)
        }

    private companion object {
        const val BOARD_KEY = "board"
    }
}
