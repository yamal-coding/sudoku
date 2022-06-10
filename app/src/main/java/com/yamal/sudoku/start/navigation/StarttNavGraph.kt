package com.yamal.sudoku.start.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yamal.sudoku.start.ui.StartScreen

@Suppress("UNUSED_PARAMETER")
fun NavGraphBuilder.startNavGraph(navController: NavController) {
    composable(
        route = StartDestination.route
    ) {
        StartScreen(
            viewModel = hiltViewModel(),
            onContinueGame = {
                // TODO not yet implemented
            },
            onStartNewGame = {
                // TODO not yet implemented
            }
        )
    }
}