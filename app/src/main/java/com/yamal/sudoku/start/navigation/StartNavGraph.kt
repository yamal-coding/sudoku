package com.yamal.sudoku.start.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yamal.sudoku.game.navigation.GameDestination
import com.yamal.sudoku.help.navigation.HelpDestination
import com.yamal.sudoku.start.ui.StartScreen
import com.yamal.sudoku.stats.navigation.StatisticsDestination

fun NavGraphBuilder.startNavGraph(navController: NavController) {
    composable(
        route = StartDestination.route
    ) {
        StartScreen(
            viewModel = hiltViewModel(),
            onStartGame = { navParams ->
                navController.navigate(GameDestination.routeFromParams(navParams))
            },
            onHowToPlayClicked = {
                navController.navigate(HelpDestination.route)
            },
            onStatisticsClicked = {
                navController.navigate(StatisticsDestination.route)
            }
        )
    }
}