package com.yamal.sudoku.game.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.yamal.sudoku.game.ui.GameScreen

fun NavGraphBuilder.gameNavGraph(
    navController: NavController,
) {
    composable(
        route =  ContinueGameDestination.route
    ) {
        GameScreenImpl(
            isNewGame = false,
            navController = navController,
        )
    }
    composable(
        route = NewGameDestination.route
    ) {
        GameScreenImpl(
            isNewGame = true,
            navController = navController,
        )
    }
}

@Composable
private fun GameScreenImpl(
    isNewGame: Boolean,
    navController: NavController,
) {
    GameScreen(
        isNewGame = isNewGame,
        viewModel = hiltViewModel(),
        onBackClicked = {
            navController.popBackStack()
        }
    )
}