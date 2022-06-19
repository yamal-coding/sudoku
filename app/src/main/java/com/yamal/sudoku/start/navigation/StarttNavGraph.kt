package com.yamal.sudoku.start.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yamal.sudoku.game.navigation.ContinueGameDestination
import com.yamal.sudoku.game.navigation.NewGameDestination
import com.yamal.sudoku.start.ui.StartScreen

fun NavGraphBuilder.startNavGraph(navController: NavController) {
    composable(
        route = StartDestination.route
    ) {
        StartScreen(
            viewModel = hiltViewModel(),
            onContinueGame = {
                navController.navigate(ContinueGameDestination.route)
            },
            onStartNewGame = {
                navController.navigate(NewGameDestination.route)
            }
        )
    }
}