package com.yamal.sudoku.game.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yamal.sudoku.game.ui.ExistingGameScreen
import com.yamal.sudoku.game.ui.NewGameScreen
import com.yamal.sudoku.model.Difficulty

fun NavGraphBuilder.gameNavGraph() {
    composable(
        route =  ContinueGameDestination.route
    ) {
        ExistingGameScreen(
            viewModel = hiltViewModel()
        )
    }
    composable(
        route = NewEasyGameDestination.route
    ) {
        NewGameScreenImpl(
            difficulty = Difficulty.EASY
        )
    }
    composable(
        route = NewMediumGameDestination.route
    ) {
        NewGameScreenImpl(
            difficulty = Difficulty.MEDIUM
        )
    }
    composable(
        route = NewHardGameDestination.route
    ) {
        NewGameScreenImpl(
            difficulty = Difficulty.HARD
        )
    }
}

@Composable
private fun NewGameScreenImpl(
    difficulty: Difficulty,
) {
    NewGameScreen(
        viewModel = hiltViewModel(),
        difficulty = difficulty,
    )
}