package com.yamal.sudoku.repository

import com.yamal.sudoku.commons.thread.JobDispatcher
import com.yamal.sudoku.model.Board
import com.yamal.sudoku.model.ReadOnlyBoard
import com.yamal.sudoku.storage.BoardStorage

class BoardRepository(
    private val boardStorage: BoardStorage,
    private val jobDispatcher: JobDispatcher
) {

    fun hasSavedBoard(onSavedBoard: () -> Unit, onNotSavedBoard: () -> Unit) {
        jobDispatcher.runOnDiskThread {
            if (boardStorage.getBoard() != null) {
                onSavedBoard()
            } else {
                onNotSavedBoard()
            }
        }
    }

    fun getSavedBoard(onBoardLoaded: (Board?) -> Unit) {
        jobDispatcher.runOnDiskThread {
            onBoardLoaded(boardStorage.getBoard()?.toDomain())
        }
    }

    fun saveBoard(onlyBoard: ReadOnlyBoard) {
        jobDispatcher.runOnDiskThread {
            boardStorage.setBoard(onlyBoard.toDO())
        }
    }

    fun removeSavedBoard() {
        jobDispatcher.runOnDiskThread {
            boardStorage.removeBoard()
        }
    }
}
