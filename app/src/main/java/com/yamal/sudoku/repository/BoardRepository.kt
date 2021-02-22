package com.yamal.sudoku.repository

import com.yamal.sudoku.commons.thread.CoroutineDispatcherProvider
import com.yamal.sudoku.model.Board
import com.yamal.sudoku.model.ReadOnlyBoard
import com.yamal.sudoku.storage.BoardStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BoardRepository(
    private val boardStorage: BoardStorage,
    private val coroutineScope: CoroutineScope,
    private val dispatchers: CoroutineDispatcherProvider
) {

    suspend fun hasSavedBoard(): Boolean = withContext(dispatchers.ioDispatcher) {
        boardStorage.board != null
    }

    suspend fun getSavedBoard(): Board? = withContext(dispatchers.ioDispatcher) {
        boardStorage.board?.toDomain()
    }

    fun saveBoard(readOnlyBoard: ReadOnlyBoard) {
        coroutineScope.launch(dispatchers.ioDispatcher) {
            boardStorage.board = readOnlyBoard.toDO()
        }
    }

    fun removeSavedBoard() {
        coroutineScope.launch(dispatchers.ioDispatcher) {
            boardStorage.board = null
        }
    }

    suspend fun shouldShowSetUpNewGameHint(): Boolean = withContext(dispatchers.ioDispatcher) {
        boardStorage.showSetUpNewGameHint
    }

    fun doNotShowSetUpNewGameHintAgain() {
        coroutineScope.launch(dispatchers.ioDispatcher) {
            boardStorage.showSetUpNewGameHint = false
        }
    }
}
