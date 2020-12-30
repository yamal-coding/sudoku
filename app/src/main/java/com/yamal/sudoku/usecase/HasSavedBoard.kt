package com.yamal.sudoku.usecase

import com.yamal.sudoku.repository.BoardRepository

class HasSavedBoard(
    private val repository: BoardRepository
) {
    operator fun invoke(onSavedBoard: () -> Unit, onNotSavedBoard: () -> Unit) {
        repository.hasSavedBoard(onSavedBoard, onNotSavedBoard)
    }
}