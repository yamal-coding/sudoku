package com.yamal.sudoku.game.level.data.utils

import javax.inject.Inject

open class LevelIdGenerator @Inject constructor() {
    fun newId(fileName: String, levelIndex: Int): String = "$fileName$SEPARATOR$levelIndex"

    fun getFileNameAndIndexFromId(id: String): Pair<String, Int>? {
        val idSegments = id.split(SEPARATOR)

        if (idSegments.size != 2 || idSegments[1].toIntOrNull() == null) {
            return null
        }

        return idSegments[0] to idSegments[1].toInt()
    }

    private companion object {
        const val SEPARATOR = "-"
    }
}