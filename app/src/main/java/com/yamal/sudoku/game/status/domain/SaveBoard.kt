package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.model.ReadOnlyBoard
import com.yamal.sudoku.game.status.data.GameStatusRepository
import javax.inject.Inject

class SaveBoard @Inject constructor(
    private val repository: GameStatusRepository
) {
    operator fun invoke(onlyBoard: ReadOnlyBoard) {
        repository.saveBoard(onlyBoard)
    }
}