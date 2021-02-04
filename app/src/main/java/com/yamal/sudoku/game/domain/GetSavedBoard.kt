package com.yamal.sudoku.game.domain

import com.yamal.sudoku.model.Board
import com.yamal.sudoku.repository.BoardRepository

class GetSavedBoard(
    private val repository: BoardRepository
) {
    suspend operator fun invoke(): Board? =
        repository.getSavedBoard()
}