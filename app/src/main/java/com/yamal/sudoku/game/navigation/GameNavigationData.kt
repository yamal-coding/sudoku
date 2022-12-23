package com.yamal.sudoku.game.navigation

import com.yamal.sudoku.model.Difficulty
import java.util.UUID

class GameNavigationParams {
    val gameId: String
    val difficulty: Difficulty

    constructor(gameId: String, difficulty: Difficulty) {
        this.gameId = gameId
        this.difficulty = difficulty
    }

    constructor(difficulty: Difficulty) {
        this.gameId = UUID.randomUUID().toString()
        this.difficulty = difficulty
    }
}
