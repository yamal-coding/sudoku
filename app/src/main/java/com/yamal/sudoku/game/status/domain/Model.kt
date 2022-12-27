package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.model.Difficulty

data class ExistingGameInfo(
    val gameId: String,
    val difficulty: Difficulty,
)

data class LastFinishedGameSummary(
    val gameId: String,
    val gameTimeInSeconds: Long,
    val isNewBestTime: Boolean,
)
