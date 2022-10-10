package com.yamal.sudoku.game.status.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class GetCurrentGameState @Inject constructor(
    private val currentGame: CurrentGame,
) {
    open operator fun invoke(): Flow<SudokuState> = currentGame.gameState
}
