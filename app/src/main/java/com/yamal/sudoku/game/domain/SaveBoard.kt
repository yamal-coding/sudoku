package com.yamal.sudoku.game.domain

import com.yamal.sudoku.model.ReadOnlyBoard
import com.yamal.sudoku.repository.BoardRepository
import javax.inject.Inject

class SaveBoard @Inject constructor(
    private val repository: BoardRepository
) {
    operator fun invoke(onlyBoard: ReadOnlyBoard) {
        repository.saveBoard(onlyBoard)
    }
}