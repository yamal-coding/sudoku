package com.yamal.sudoku.stats.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yamal.sudoku.stats.ui.StatsScreen

fun NavGraphBuilder.statisticsNavGraph() {
    composable(
        route = StatisticsDestination.route
    ) {
        StatsScreen(
            viewModel = hiltViewModel(),
        )
    }
}
