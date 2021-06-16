package com.yamal.sudoku.game.domain

import com.yamal.sudoku.repository.BoardRepository
import javax.inject.Inject

class ShouldShowSetUpNewGameHint @Inject constructor(
    private val repository: BoardRepository
) {
    suspend operator fun invoke(): Boolean =
        repository.shouldShowSetUpNewGameHint()
}