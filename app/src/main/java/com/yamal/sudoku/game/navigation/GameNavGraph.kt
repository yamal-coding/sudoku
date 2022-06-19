package com.yamal.sudoku.game.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.gameNavGraph() {
    composable(
        route =  ContinueGameDestination.route
    ) {
        // TODO
    }
    composable(
        route = NewGameDestination.route
    ) {
        // TODO
    }
}