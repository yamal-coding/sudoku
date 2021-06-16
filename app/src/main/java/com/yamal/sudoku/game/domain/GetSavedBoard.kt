package com.yamal.sudoku.game.domain

import com.yamal.sudoku.model.Board
import com.yamal.sudoku.repository.BoardRepository
import javax.inject.Inject

class GetSavedBoard @Inject constructor(
    private val repository: BoardRepository
) {
    suspend operator fun invoke(): Board? =
        repository.getSavedBoard()
}