package com.yamal.sudoku.game.ui

import com.yamal.sudoku.commons.navigation.SudokuNavDestination
import com.yamal.sudoku.game.navigation.NewEasyGameDestination
import com.yamal.sudoku.game.navigation.NewHardGameDestination
import com.yamal.sudoku.game.navigation.NewMediumGameDestination

enum class DifficultyViewData {
    EASY,
    MEDIUM,
    HARD;
}

fun DifficultyViewData.toNavDestination(): SudokuNavDestination =
    when (this) {
        DifficultyViewData.EASY -> NewEasyGameDestination
        DifficultyViewData.MEDIUM -> NewMediumGameDestination
        DifficultyViewData.HARD -> NewHardGameDestination
    }