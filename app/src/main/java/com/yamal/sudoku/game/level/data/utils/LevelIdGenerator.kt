package com.yamal.sudoku.game.level.data.utils

import javax.inject.Inject

open class LevelIdGenerator @Inject constructor() {
    fun newId(fileName: String, levelIndex: Int): String = "$fileName-$levelIndex"
}