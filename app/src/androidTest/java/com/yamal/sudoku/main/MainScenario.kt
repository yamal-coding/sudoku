package com.yamal.sudoku.main

import com.yamal.sudoku.storage.BoardStorage
import com.yamal.sudoku.storage.model.BoardDO
import javax.inject.Inject

class MainScenario @Inject constructor(
    private val boardStorage: BoardStorage
) {
    fun givenNoSavedGame() {
        boardStorage.board = null
    }

    fun givenAnySavedGame() {
        boardStorage.board = BoardDO(listOf(listOf()), difficulty = null)
    }
}