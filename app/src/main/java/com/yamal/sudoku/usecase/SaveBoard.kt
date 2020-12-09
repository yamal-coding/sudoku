package com.yamal.sudoku.usecase

import com.yamal.sudoku.model.ReadOnlyBoard
import com.yamal.sudoku.repository.BoardRepository

class SaveBoard(
    private val repository: BoardRepository
) {
    operator fun invoke(onlyBoard: ReadOnlyBoard) {
        repository.saveBoard(onlyBoard)
    }
}