package com.yamal.sudoku.main.domain

import com.yamal.sudoku.repository.BoardRepository

class HasSavedBoard(
    private val repository: BoardRepository
) {
    suspend operator fun invoke(): Boolean =
        repository.hasSavedBoard()
}