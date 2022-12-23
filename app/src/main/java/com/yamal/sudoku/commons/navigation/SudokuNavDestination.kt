package com.yamal.sudoku.commons.navigation

import androidx.navigation.NamedNavArgument

abstract class SudokuNavDestination {
    abstract val route: String
    open val arguments: List<NamedNavArgument> = emptyList()
}