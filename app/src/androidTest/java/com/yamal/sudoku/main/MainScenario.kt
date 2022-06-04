package com.yamal.sudoku.main

import com.yamal.sudoku.game.status.data.storage.GameStatusStorage
import com.yamal.sudoku.game.status.data.storage.model.BoardDO
import javax.inject.Inject

class MainScenario @Inject constructor(
    private val gameStatusStorage: GameStatusStorage
) {
    fun givenNoSavedGame() {
        gameStatusStorage.board = null
    }

    fun givenAnySavedGame() {
        gameStatusStorage.board = BoardDO(listOf())
    }
}