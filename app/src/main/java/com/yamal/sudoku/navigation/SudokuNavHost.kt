package com.yamal.sudoku.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.yamal.sudoku.game.navigation.gameNavGraph
import com.yamal.sudoku.help.navigation.helpNavGraph
import com.yamal.sudoku.start.navigation.StartDestination
import com.yamal.sudoku.start.navigation.startNavGraph
import com.yamal.sudoku.stats.navigation.statisticsNavGraph

@Composable
fun SudokuNavHost(
    modifier: Modifier,
) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = StartDestination.route
    ) {
        startNavGraph(navController)
        gameNavGraph(navController)
        helpNavGraph()
        statisticsNavGraph()
    }
}