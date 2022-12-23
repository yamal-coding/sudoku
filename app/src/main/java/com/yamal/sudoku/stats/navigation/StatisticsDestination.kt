package com.yamal.sudoku.stats.navigation

import com.yamal.sudoku.commons.navigation.SudokuNavDestination

object StatisticsDestination : SudokuNavDestination() {
    override val route: String
        get() = "statistics"
}
