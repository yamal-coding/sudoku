package com.yamal.sudoku.usecase

import com.yamal.sudoku.repository.BoardRepository

class RemoveSavedBoard(
    private val repository: BoardRepository
) {
    operator fun invoke() {
        repository.removeSavedBoard()
    }
}