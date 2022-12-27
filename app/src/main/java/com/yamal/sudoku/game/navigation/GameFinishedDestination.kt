package com.yamal.sudoku.game.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.yamal.sudoku.commons.navigation.SudokuNavDestination

object GameFinishedDestination : SudokuNavDestination() {
    override val route: String
        get() = "$BASE_ROUTE/{$GAME_ID_PARAM}"

    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(name = GAME_ID_PARAM) { NavType.StringType }
        )

    fun routeFromParams(gameId: String) =
        "$BASE_ROUTE/$gameId"

    private const val BASE_ROUTE = "game-finished"
    const val GAME_ID_PARAM = "gameId"
}
