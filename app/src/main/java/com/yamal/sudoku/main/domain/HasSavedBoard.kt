package com.yamal.sudoku.main.domain

import com.yamal.sudoku.repository.BoardRepository
import javax.inject.Inject

class HasSavedBoard @Inject constructor(
    private val repository: BoardRepository
) {
    suspend operator fun invoke(): Boolean =
        repository.hasSavedBoard()
}