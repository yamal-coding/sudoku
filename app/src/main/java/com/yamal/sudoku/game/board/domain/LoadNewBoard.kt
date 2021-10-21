package com.yamal.sudoku.game.board.domain

import com.yamal.sudoku.game.board.data.BoardsRepository
import com.yamal.sudoku.model.Board
import javax.inject.Inject

class LoadNewBoard @Inject constructor(
    private val boardsRepository: BoardsRepository
) {
    operator fun invoke(): Board? = boardsRepository.getNewBoard()
}