package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.game.domain.Board
import com.yamal.sudoku.game.domain.Game
import javax.inject.Inject

open class GameFactory @Inject constructor() {
    open fun get(board: Board): Game =
        Game(board)
}
