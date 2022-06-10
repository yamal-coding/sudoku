package com.yamal.sudoku.test.mocks

import com.yamal.sudoku.game.level.data.LevelDO
import com.yamal.sudoku.game.level.data.datasource.LevelsDataSource
import com.yamal.sudoku.model.Difficulty
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeLevelsDataSourceImpl @Inject constructor() : LevelsDataSource {
    override fun getNewLevel(difficulty: Difficulty): LevelDO? {
        TODO("Not yet implemented")
    }
/*
    private var mockedBoard: NewTypeBoard? = null

    override fun getNewLevel(): NewTypeBoard? =
        mockedBoard ?: throw IllegalStateException("A mocked board should have been set first.")

    override fun getNewLevel(difficulty: Difficulty): NewTypeBoard? {
        TODO("Not yet implemented")
    }

    fun whenGettingNewBoard(thenReturn: ReadOnlyBoard) {
        mockedBoard = thenReturn
    }

    fun reset() {
        mockedBoard = null
    }*/
}
