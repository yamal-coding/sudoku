package com.yamal.sudoku.game.scenario

import com.yamal.sudoku.game.status.data.storage.GameStatusStorage
import com.yamal.sudoku.game.status.data.storage.model.BoardDO
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class GameScenario @Inject constructor(
    private val gameStatusStorage: GameStatusStorage
) {
    fun givenThereIsAnExistingGame(boardDO: BoardDO) {
        runBlocking {
            gameStatusStorage.updateBoard(boardDO)
        }
    }
}
