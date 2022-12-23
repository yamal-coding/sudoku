package com.yamal.sudoku.game.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.yamal.sudoku.commons.navigation.SudokuNavDestination
import com.yamal.sudoku.model.Difficulty

object GameDestination : SudokuNavDestination() {
    override val route: String
        get() = "$BASE_ROUTE/{$DIFFICULTY_PARAM}/{$GAME_ID_PARAM}"

    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(DIFFICULTY_PARAM) {
                type = NavType.StringType
            },
            navArgument(GAME_ID_PARAM) {
                type = NavType.StringType
            },
        )

    private const val BASE_ROUTE = "game"
    const val DIFFICULTY_PARAM = "difficulty"
    const val GAME_ID_PARAM = "game_id"

    private const val EASY_DIFFICULTY_PARAM_VALUE = "easy"
    private const val MEDIUM_DIFFICULTY_PARAM_VALUE = "medium"
    private const val HARD_DIFFICULTY_PARAM_VALUE = "hard"

    fun difficultyFromParam(difficultyParam: String): Difficulty =
        when (difficultyParam) {
            EASY_DIFFICULTY_PARAM_VALUE -> Difficulty.EASY
            MEDIUM_DIFFICULTY_PARAM_VALUE -> Difficulty.MEDIUM
            HARD_DIFFICULTY_PARAM_VALUE -> Difficulty.HARD
            else -> throw IllegalArgumentException(
                "Unknown difficulty navigation param in $route destination: $difficultyParam"
            )
        }

    fun routeFromParams(params: GameNavigationParams): String =
        "$BASE_ROUTE/${params.difficulty.toNavArg()}/${params.gameId}"

    private fun Difficulty.toNavArg(): String =
        when (this) {
            Difficulty.EASY -> EASY_DIFFICULTY_PARAM_VALUE
            Difficulty.MEDIUM -> MEDIUM_DIFFICULTY_PARAM_VALUE
            Difficulty.HARD -> HARD_DIFFICULTY_PARAM_VALUE
        }
}