package com.yamal.sudoku.game.status.data

import com.yamal.sudoku.commons.thread.ApplicationScope
import com.yamal.sudoku.commons.thread.di.IODispatcher
import com.yamal.sudoku.game.domain.Board
import com.yamal.sudoku.game.domain.ReadOnlyBoard
import com.yamal.sudoku.game.status.data.storage.GameStatusStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class GameStatusRepository @Inject constructor(
    private val gameStatusStorage: GameStatusStorage,
    private val scope: ApplicationScope,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    open fun getSavedBoard(): Flow<Board?> =
        gameStatusStorage.observeBoard().map { it?.toDomain() }

    open suspend fun getSavedBoardSync(): Board? = withContext(ioDispatcher) {
        getSavedBoard().firstOrNull()
    }

    open fun saveBoard(readOnlyBoard: ReadOnlyBoard) {
        scope.launch(ioDispatcher) {
            gameStatusStorage.updateBoard(readOnlyBoard.toDO())
        }
    }

    open fun removeSavedBoard() {
        scope.launch(ioDispatcher) {
            gameStatusStorage.updateBoard(null)
        }
    }

    open fun saveTimeCounter(timeCounter: Long?) {
        scope.launch(ioDispatcher) {
            gameStatusStorage.updateTimeCounter(timeCounter)
        }
    }

    open suspend fun getTimeCounterSync(): Long? = withContext(ioDispatcher) {
        gameStatusStorage.getTimeCounter()
    }
}
