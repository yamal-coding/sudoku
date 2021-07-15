package com.yamal.sudoku.main.domain

import com.yamal.sudoku.game.status.data.GameStatusRepository
import javax.inject.Inject

class HasSavedBoard @Inject constructor(
    private val repository: GameStatusRepository
) {
    suspend operator fun invoke(): Boolean =
        repository.hasSavedBoard()
}