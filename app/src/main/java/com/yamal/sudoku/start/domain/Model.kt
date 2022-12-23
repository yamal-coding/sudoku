package com.yamal.sudoku.start.domain

import com.yamal.sudoku.model.Difficulty

data class ExistingGameInfo(
    val gameId: String,
    val difficulty: Difficulty,
)
