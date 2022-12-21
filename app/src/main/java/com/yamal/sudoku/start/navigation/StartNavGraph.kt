package com.yamal.sudoku.start.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yamal.sudoku.game.navigation.ContinueGameDestination
import com.yamal.sudoku.game.ui.toNavDestination
import com.yamal.sudoku.help.navigation.HelpDestination
import com.yamal.sudoku.start.ui.StartScreen
import com.yamal.sudoku.stats.navigation.StatisticsDestination

fun NavGraphBuilder.startNavGraph(navController: NavController) {
    composable(
        route = StartDestination.route
    ) {
        StartScreen(
            viewModel = hiltViewModel(),
            onContinueGame = {
                navController.navigate(ContinueGameDestination.route)
            },
            onNewGame = { difficulty ->
                navController.navigate(difficulty.toNavDestination().route)
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