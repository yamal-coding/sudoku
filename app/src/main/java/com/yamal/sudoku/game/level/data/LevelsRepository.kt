package com.yamal.sudoku.game.level.data

import com.yamal.sudoku.model.Board
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LevelsRepository @Inject constructor(
    private val dataSource: LevelsDataSource
) {
    fun getNewLevel(): Board? =
        dataSource.getNewLevel()
}
