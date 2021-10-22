package com.yamal.sudoku.game.level.data

import javax.inject.Inject

open class LevelIdGenerator @Inject constructor() {
    fun newId(fileName: String, levelIndex: Int): String = "$fileName-$levelIndex"
}