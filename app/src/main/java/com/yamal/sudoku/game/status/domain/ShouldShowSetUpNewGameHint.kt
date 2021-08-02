package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.game.status.data.GameStatusRepository
import javax.inject.Inject

class ShouldShowSetUpNewGameHint @Inject constructor(
    private val repository: GameStatusRepository
) {
    suspend operator fun invoke(): Boolean =
        repository.shouldShowSetUpNewGameHint()
}