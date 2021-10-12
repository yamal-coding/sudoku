package com.yamal.sudoku.game.board.data

import javax.inject.Inject

open class LevelsFileProvider @Inject constructor() {
    open fun get(fileName: String): LevelsFile =
        LevelsFile(fileName)
}