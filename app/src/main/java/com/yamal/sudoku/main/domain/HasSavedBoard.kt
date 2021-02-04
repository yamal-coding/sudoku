package com.yamal.sudoku.main.domain

import com.yamal.sudoku.repository.BoardRepository

class HasSavedBoard(
    private val repository: BoardRepository
) {
    operator fun invoke(onSavedBoard: () -> Unit, onNotSavedBoard: () -> Unit) {
        repository.hasSavedBoard(onSavedBoard, onNotSavedBoard)
    }
}