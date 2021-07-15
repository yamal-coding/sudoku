package com.yamal.sudoku.game.board.data

import com.yamal.sudoku.game.status.data.toDO
import com.yamal.sudoku.model.Board
import com.yamal.sudoku.storage.model.BoardDO
import javax.inject.Inject
import javax.inject.Singleton

interface BoardsDataSource {
    fun getNewBoard(): BoardDO
}

@Singleton
class BoardsDataSourceImpl @Inject constructor() : BoardsDataSource {
    // TODO Retrieve from repository once a pre-populated database is created with several levels
    override fun getNewBoard(): BoardDO =
        Board.almostDone().toDO()
}
