package com.yamal.sudoku.game.status.data

import com.yamal.sudoku.commons.thread.ApplicationScope
import com.yamal.sudoku.commons.thread.CoroutineDispatcherProvider
import com.yamal.sudoku.model.Board
import com.yamal.sudoku.model.ReadOnlyBoard
import com.yamal.sudoku.storage.BoardStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameStatusRepository @Inject constructor(
    private val boardStorage: BoardStorage,
    private val scope: ApplicationScope,
    private val dispatchers: CoroutineDispatcherProvider
) {

    suspend fun hasSavedBoard(): Boolean = withContext(dispatchers.ioDispatcher) {
        boardStorage.board != null
    }

    suspend fun getSavedBoard(): Board? = withContext(dispatchers.ioDispatcher) {
        boardStorage.board?.toDomain()
    }

    fun saveBoard(readOnlyBoard: ReadOnlyBoard) {
        scope.launch(dispatchers.ioDispatcher) {
            boardStorage.board = readOnlyBoard.toDO()
        }
    }

    fun removeSavedBoard() {
        scope.launch(dispatchers.ioDispatcher) {
            boardStorage.board = null
        }
    }

    suspend fun shouldShowSetUpNewGameHint(): Boolean = withContext(dispatchers.ioDispatcher) {
        boardStorage.showSetUpNewGameHint
    }

    fun doNotShowSetUpNewGameHintAgain() {
        scope.launch(dispatchers.ioDispatcher) {
            boardStorage.showSetUpNewGameHint = false
        }
    }
}
