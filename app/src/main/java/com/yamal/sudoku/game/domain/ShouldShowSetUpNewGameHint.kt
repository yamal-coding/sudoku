package com.yamal.sudoku.game.domain

import com.yamal.sudoku.repository.BoardRepository

class ShouldShowSetUpNewGameHint(
    private val repository: BoardRepository
) {
    suspend operator fun invoke(): Boolean =
        repository.shouldShowSetUpNewGameHint()
}