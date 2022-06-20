package com.yamal.sudoku.game.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yamal.sudoku.game.ui.GameScreen

fun NavGraphBuilder.gameNavGraph() {
    composable(
        route =  ContinueGameDestination.route
    ) {
        GameScreen(
            isNewGame = false
        )
    }
    composable(
        route = NewGameDestination.route
    ) {
        GameScreen(
            isNewGame = true
        )
    }
}