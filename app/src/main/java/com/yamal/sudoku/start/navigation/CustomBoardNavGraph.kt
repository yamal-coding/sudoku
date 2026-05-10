package com.yamal.sudoku.start.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yamal.sudoku.game.navigation.GameDestination
import com.yamal.sudoku.start.ui.CustomBoardScreen

fun NavGraphBuilder.customBoardNavGraph(navController: NavController) {
    composable(
        route = CustomBoardDestination.route,
    ) {
        CustomBoardScreen(
            viewModel = hiltViewModel(),
            onStartGame = { navParams ->
                navController.navigate(GameDestination.routeFromParams(navParams)) {
                    popUpTo(CustomBoardDestination.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}
