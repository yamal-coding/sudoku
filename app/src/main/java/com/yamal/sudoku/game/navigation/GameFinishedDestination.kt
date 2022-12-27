package com.yamal.sudoku.game.navigation

import com.yamal.sudoku.commons.navigation.SudokuNavDestination

object GameFinishedDestination : SudokuNavDestination() {
    override val route: String
        get() = "game-finished"
}
