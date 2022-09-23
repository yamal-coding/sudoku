package com.yamal.sudoku.help.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yamal.sudoku.help.ui.HelpScreen

fun NavGraphBuilder.helpNavGraph() {
    composable(
        route = HelpDestination.route
    ) {
        HelpScreen()
    }
}
