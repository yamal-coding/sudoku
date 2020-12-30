package com.yamal.sudoku.repository

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.yamal.sudoku.R
import com.yamal.sudoku.commons.JobDispatcher
import com.yamal.sudoku.commons.JobDispatcherImpl
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

    companion object { // TODO add dependency injector or something better
        private var instance: BoardRepository? = null

        fun getInstance(context: Context): BoardRepository =
            if (instance == null) {
                val gson = GsonBuilder().create()
                val sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences_file_name),
                    AppCompatActivity.MODE_PRIVATE
                )
                val storage = BoardStorage(gson, sharedPreferences)
                val jobDispatcher = JobDispatcherImpl()
                BoardRepository(storage, jobDispatcher)
            } else {
                instance!!
            }
    }
}
