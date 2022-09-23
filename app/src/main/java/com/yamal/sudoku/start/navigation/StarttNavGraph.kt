package com.yamal.sudoku.start.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yamal.sudoku.game.navigation.ContinueGameDestination
import com.yamal.sudoku.game.navigation.NewEasyGameDestination
import com.yamal.sudoku.game.navigation.NewHardGameDestination
import com.yamal.sudoku.game.navigation.NewMediumGameDestination
import com.yamal.sudoku.help.navigation.HelpDestination
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
            onStartNewEasyGame = {
                navController.navigate(NewEasyGameDestination.route)
            },
            onStartNewMediumGame = {
                navController.navigate(NewMediumGameDestination.route)
            },
            onStartNewHardGame = {
                navController.navigate(NewHardGameDestination.route)
            },
            onHowToPlayClicked = {
                navController.navigate(HelpDestination.route)
            }
        )
    }
}