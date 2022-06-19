package com.yamal.sudoku.game.navigation

import com.yamal.sudoku.commons.navigation.SudokuNavDestination

object NewGameDestination : SudokuNavDestination {
    override val route: String
        get() = "new_game"
}

object ContinueGameDestination : SudokuNavDestination {
    override val route: String
        get() = "continue_game"
}