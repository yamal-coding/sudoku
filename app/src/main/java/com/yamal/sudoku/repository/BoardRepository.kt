package com.yamal.sudoku.repository

import com.yamal.sudoku.commons.JobDispatcher
import com.yamal.sudoku.model.Board
import com.yamal.sudoku.model.ReadOnlyBoard
import com.yamal.sudoku.storage.BoardStorage

class BoardRepository(
    private val boardStorage: BoardStorage,
    private val jobDispatcher: JobDispatcher
) {

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
}
