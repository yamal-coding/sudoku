package com.yamal.sudoku.game.domain

import com.yamal.sudoku.model.Board

class LoadNewGame {
    // TODO Retrieve from repository once a prepopulated database is created with several levels
    operator fun invoke(): Board = Board.almostDone()
}