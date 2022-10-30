package com.yamal.sudoku.game.scenario

import com.yamal.sudoku.game.status.data.storage.GameStatusStorage
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class GameScenario @Inject constructor(
    private val gameStatusStorage: GameStatusStorage
) {
    fun givenThereIsAnExistingGameInProgress() {
        runBlocking {
            gameStatusStorage.updateBoard(SudokuMother.someBoardDO())
        }
    }
}
