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
        boardStorage.getBoard() != null
    }

    suspend fun getSavedBoard(): Board? = withContext(dispatchers.ioDispatcher) {
        boardStorage.getBoard()?.toDomain()
    }

    fun saveBoard(onlyBoard: ReadOnlyBoard) {
        coroutineScope.launch(dispatchers.ioDispatcher) {
            boardStorage.setBoard(onlyBoard.toDO())
        }
    }

    fun removeSavedBoard() {
        coroutineScope.launch(dispatchers.ioDispatcher) {
            boardStorage.removeBoard()
        }
    }
}
