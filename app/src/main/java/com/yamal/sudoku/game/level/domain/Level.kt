package com.yamal.sudoku.game.level.domain

import com.yamal.sudoku.game.domain.Board
import com.yamal.sudoku.model.Difficulty

data class Level(
    val id: String,
    val difficulty: Difficulty,
    val board: Board
)
