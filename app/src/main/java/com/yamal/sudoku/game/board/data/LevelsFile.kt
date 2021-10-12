package com.yamal.sudoku.game.board.data

@Suppress("UNUSED_PARAMETER")
class LevelsFile(fileName: String) {

    var numOfBoards: Int = 0
    private set
    private val loadedLevels = mutableListOf<String>()

    fun open(): Boolean {
        TODO()
    }

    fun getRawLevel(levelIndex: Int): String {
        if (levelIndex >= numOfBoards) {
            throw IllegalArgumentException("There are only $numOfBoards levels, can't return level number $levelIndex")
        }

        if (levelIndex >= loadedLevels.size) {
            var i = loadedLevels.size - 1
            while (i < levelIndex) {
                loadedLevels.add(readLine())
                i++
            }
        }

        return loadedLevels[levelIndex]
    }

    private fun readLine(): String {
        // read next line in the file equivalent to a raw level
        TODO()
    }
}