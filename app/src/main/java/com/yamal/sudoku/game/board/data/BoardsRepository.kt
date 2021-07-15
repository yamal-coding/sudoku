package com.yamal.sudoku.game.board.data

import com.yamal.sudoku.game.status.data.toDomain
import com.yamal.sudoku.model.Board
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BoardsRepository @Inject constructor(
    private val dataSource: BoardsDataSource
) {
    fun getNewBoard(): Board =
        dataSource.getNewBoard().toDomain()
}
