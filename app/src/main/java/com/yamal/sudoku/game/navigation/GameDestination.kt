package com.yamal.sudoku.game.navigation

import com.yamal.sudoku.commons.navigation.SudokuNavDestination

object NewEasyGameDestination : SudokuNavDestination {
    override val route: String
        get() = "new_easy_game"
}

object NewMediumGameDestination : SudokuNavDestination {
    override val route: String
        get() = "new_medium_game"
}

object NewHardGameDestination : SudokuNavDestination {
    override val route: String
        get() = "new_hard_game"
}

object ContinueGameDestination : SudokuNavDestination {
    override val route: String
        get() = "continue_game"
}