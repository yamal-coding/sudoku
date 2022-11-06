package com.yamal.sudoku.game.ui

object GameTestTags {
    const val SCREEN = "game"
    const val FINISHED_SCREEN = "game_finished"
    const val REMOVE_BUTTON = "remove"
    const val ONE_BUTTON = "one"
    const val TWO_BUTTON = "two"
    const val THREE_BUTTON = "three"
    const val FOUR_BUTTON = "four"
    const val FIVE_BUTTON = "five"
    const val SIX_BUTTON = "six"
    const val SEVEN_BUTTON = "seven"
    const val EIGHT_BUTTON = "eight"
    const val NINE_BUTTON = "nine"

    private val BOARD_TEST_TAGS = mutableMapOf<Pair<Int, Int>, String>()

    fun cell(row: Int, column: Int): String =
        BOARD_TEST_TAGS[row to column] ?: "cell_${row}_$column".also {
            BOARD_TEST_TAGS[row to column] = it
        }
}