package com.yamal.sudoku.game.level.data

import com.yamal.sudoku.model.Difficulty

data class LevelDO(
    val id: String,
    val difficulty: Difficulty,
    val rawBoard: String
)
