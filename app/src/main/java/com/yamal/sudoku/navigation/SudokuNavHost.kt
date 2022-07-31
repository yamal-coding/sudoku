package com.yamal.sudoku.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.yamal.sudoku.game.navigation.gameNavGraph
import com.yamal.sudoku.start.navigation.StartDestination
import com.yamal.sudoku.start.navigation.startNavGraph

@Composable
fun SudokuNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = StartDestination.route
    ) {
        startNavGraph(navController)
        gameNavGraph()
    }
}