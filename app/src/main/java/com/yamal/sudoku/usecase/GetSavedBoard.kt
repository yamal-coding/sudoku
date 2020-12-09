package com.yamal.sudoku.usecase

import com.yamal.sudoku.model.Board
import com.yamal.sudoku.repository.BoardRepository

class GetSavedBoard(
    private val repository: BoardRepository
) {
    operator fun invoke(onBoardLoaded: (Board?) -> Unit) {
        repository.getSavedBoard(onBoardLoaded)
    }
}