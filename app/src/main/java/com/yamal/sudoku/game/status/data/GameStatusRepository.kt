package com.yamal.sudoku.game.status.data

import com.yamal.sudoku.commons.thread.ApplicationScope
import com.yamal.sudoku.commons.thread.di.IODispatcher
import com.yamal.sudoku.game.domain.Board
import com.yamal.sudoku.game.domain.ReadOnlyBoard
import com.yamal.sudoku.game.status.data.storage.GameStatusStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameStatusRepository @Inject constructor(
    private val gameStatusStorage: GameStatusStorage,
    private val scope: ApplicationScope,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) {

    suspend fun hasSavedBoard(): Boolean = withContext(ioDispatcher) {
        gameStatusStorage.board != null
    }

    suspend fun getSavedBoard(): Board? = withContext(ioDispatcher) {
        gameStatusStorage.board?.toDomain()
    }

    fun saveBoard(readOnlyBoard: ReadOnlyBoard) {
        scope.launch(ioDispatcher) {
            gameStatusStorage.board = readOnlyBoard.toDO()
        }
    }

    fun removeSavedBoard() {
        scope.launch(ioDispatcher) {
            gameStatusStorage.board = null
        }
    }
}
