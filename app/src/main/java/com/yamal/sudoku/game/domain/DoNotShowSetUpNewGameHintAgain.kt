package com.yamal.sudoku.game.domain

import com.yamal.sudoku.repository.BoardRepository

class DoNotShowSetUpNewGameHintAgain(
    private val repository: BoardRepository
) {
    operator fun invoke() {
        repository.doNotShowSetUpNewGameHintAgain()
    }
}