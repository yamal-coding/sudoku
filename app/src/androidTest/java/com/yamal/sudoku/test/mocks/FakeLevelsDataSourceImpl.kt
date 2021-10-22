package com.yamal.sudoku.test.mocks

import com.yamal.sudoku.game.level.data.LevelsDataSource
import com.yamal.sudoku.model.Board
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.model.ReadOnlyBoard
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeLevelsDataSourceImpl @Inject constructor() : LevelsDataSource {

    private var mockedBoard: ReadOnlyBoard? = null

    override fun getNewLevel(): Board? =
        mockedBoard ?: throw IllegalStateException("A mocked board should have been set first.")

    override fun getNewLevel(difficulty: Difficulty): Board? {
        TODO("Not yet implemented")
    }

    fun whenGettingNewBoard(thenReturn: ReadOnlyBoard) {
        mockedBoard = thenReturn
    }

    fun reset() {
        mockedBoard = null
    }
}
