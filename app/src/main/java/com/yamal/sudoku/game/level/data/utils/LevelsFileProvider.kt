package com.yamal.sudoku.game.level.data.utils

import com.yamal.sudoku.game.level.data.datasource.LevelsFile
import javax.inject.Inject

open class LevelsFileProvider @Inject constructor() {
    open fun get(fileName: String): LevelsFile =
        LevelsFile(fileName)
}