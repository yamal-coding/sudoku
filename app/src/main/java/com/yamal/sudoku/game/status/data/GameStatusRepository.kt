package com.yamal.sudoku.game.status.data

import com.yamal.sudoku.commons.thread.ApplicationScope
import com.yamal.sudoku.commons.thread.di.IODispatcher
import com.yamal.sudoku.game.domain.Board
import com.yamal.sudoku.game.domain.ReadOnlyBoard
import com.yamal.sudoku.game.status.data.storage.GameStatusStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameStatusRepository @Inject constructor(
    private val gameStatusStorage: GameStatusStorage,
    private val scope: ApplicationScope,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    fun getSavedBoard(): Flow<Board?> =
        gameStatusStorage.observeBoard().map { it?.toDomain() }

    fun saveBoard(readOnlyBoard: ReadOnlyBoard) {
        scope.launch(ioDispatcher) {
            gameStatusStorage.updateBoard(readOnlyBoard.toDO())
        }
    }

    fun removeSavedBoard() {
        scope.launch(ioDispatcher) {
            gameStatusStorage.updateBoard(null)
        }
    }
}
