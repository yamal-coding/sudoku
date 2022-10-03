package com.yamal.sudoku.game.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yamal.sudoku.game.ui.DifficultyViewData
import com.yamal.sudoku.game.ui.ExistingGameScreen
import com.yamal.sudoku.game.ui.NewGameScreen
import com.yamal.sudoku.game.ui.toNavDestination
import com.yamal.sudoku.model.Difficulty

fun NavGraphBuilder.gameNavGraph(navController: NavController) {
    composable(
        route =  ContinueGameDestination.route
    ) {
        ExistingGameScreen(
            viewModel = hiltViewModel(),
            onNewGame = {
                navController.onNewGame(it)
            },
            onBackToMenu = {
                navController.onBackToMenu()
            },
        )
    }
    composable(
        route = NewEasyGameDestination.route
    ) {
        NewGameScreenImpl(
            navController = navController,
            difficulty = Difficulty.EASY,
        )
    }
    composable(
        route = NewMediumGameDestination.route
    ) {
        NewGameScreenImpl(
            navController = navController,
            difficulty = Difficulty.MEDIUM,
        )
    }
    composable(
        route = NewHardGameDestination.route
    ) {
        NewGameScreenImpl(
            navController = navController,
            difficulty = Difficulty.HARD,
        )
    }
}

@Composable
private fun NewGameScreenImpl(
    navController: NavController,
    difficulty: Difficulty,
) {
    NewGameScreen(
        viewModel = hiltViewModel(),
        difficulty = difficulty,
        onNewGame = {
            navController.onNewGame(it)
        },
        onBackToMenu = {
            navController.onBackToMenu()
        },
    )
}

private fun NavController.onNewGame(difficulty: DifficultyViewData) {
    popBackStack()
    navigate(difficulty.toNavDestination().route)
}

private fun NavController.onBackToMenu() {
    popBackStack()
}