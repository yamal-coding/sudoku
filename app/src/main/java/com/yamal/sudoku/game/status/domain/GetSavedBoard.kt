package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.game.domain.Board
import com.yamal.sudoku.game.status.data.GameStatusRepository
import javax.inject.Inject

class GetSavedBoard @Inject constructor(
    private val repository: GameStatusRepository
) {
    suspend operator fun invoke(): Board? =
        repository.getSavedBoard()
}