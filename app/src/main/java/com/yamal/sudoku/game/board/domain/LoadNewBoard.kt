package com.yamal.sudoku.game.board.domain

import com.yamal.sudoku.model.Board
import javax.inject.Inject

class LoadNewBoard @Inject constructor() {
    // TODO Retrieve from repository once a prepopulated database is created with several levels
    operator fun invoke(): Board = Board.almostDone()
}