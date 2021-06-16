package com.yamal.sudoku.game.domain

import com.yamal.sudoku.repository.BoardRepository
import javax.inject.Inject

class RemoveSavedBoard @Inject constructor(
    private val repository: BoardRepository
) {
    operator fun invoke() {
        repository.removeSavedBoard()
    }
}