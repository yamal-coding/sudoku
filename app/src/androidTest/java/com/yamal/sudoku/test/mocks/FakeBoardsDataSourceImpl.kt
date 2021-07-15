package com.yamal.sudoku.test.mocks

import com.yamal.sudoku.game.board.data.BoardsDataSource
import com.yamal.sudoku.game.status.data.toDO
import com.yamal.sudoku.model.ReadOnlyBoard
import com.yamal.sudoku.storage.model.BoardDO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeBoardsDataSourceImpl @Inject constructor() : BoardsDataSource {

    private var mockedBoard: ReadOnlyBoard? = null

    override fun getNewBoard(): BoardDO =
        mockedBoard?.toDO() ?: throw IllegalStateException("Mocked board should be have been set first.")

    fun whenGettingNewBoard(thenReturn: ReadOnlyBoard) {
        mockedBoard = thenReturn
    }

    fun reset() {
        mockedBoard = null
    }
}
