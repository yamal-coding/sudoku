package com.yamal.sudoku.game.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yamal.sudoku.game.ui.GameFinishedScreen
import com.yamal.sudoku.game.ui.GameScreen

fun NavGraphBuilder.gameNavGraph(navController: NavController) {
    composable(
        route = GameDestination.route,
        arguments = GameDestination.arguments
    ) {
        val gameId = it.arguments?.getString(GameDestination.GAME_ID_PARAM)!!
        val difficulty = it.arguments?.getString(GameDestination.DIFFICULTY_PARAM)!!

        GameScreen(
            viewModel = hiltViewModel(),
            gameId = gameId,
            difficulty = GameDestination.difficultyFromParam(difficulty),
            onBackToMenu = {
                navController.onBackToMenu()
            },
            onGameFinished = {
                navController.onGameFinished()
            },
        )
    }
    composable(
        route = GameFinishedDestination.route
    ) {
        GameFinishedScreen(
            viewModel = hiltViewModel(),
            onNewGame = { navParams ->
                navController.onNewGame(navParams)
            },
            onBackToMenu = {
                navController.onBackToMenu()
            },
        )
    }
}

private fun NavController.onNewGame(navParams: GameNavigationParams) {
    popBackStack()
    navigate(GameDestination.routeFromParams(navParams))
}

private fun NavController.onBackToMenu() {
    popBackStack()
}

private fun NavController.onGameFinished() {
    popBackStack()
    navigate(GameFinishedDestination.route)
}