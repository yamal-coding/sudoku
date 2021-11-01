package com.yamal.sudoku.game.level.data.datasource

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.util.Scanner

open class LevelsFile(private val fileName: String) {

    private var numOfBoards: Int = 0
    private val loadedLevels = mutableListOf<String>()

    private var scanner: Scanner? = null
    private var inputStream: InputStream? = null

    open fun getNumOfBoards(): Int =
        numOfBoards

    open fun open(context: Context): Boolean =
        try {
            val inputStream = context.assets.open(fileName)
            this.inputStream = inputStream
            val scanner = Scanner(inputStream)
            this.scanner = scanner

            numOfBoards = scanner.nextLine().toInt()

            true
        } catch (e: IOException) {
            false
        } catch (e: NumberFormatException) {
            false
        }

    open fun getRawLevel(levelIndex: Int): String? {
        if (levelIndex >= numOfBoards) {
            throw IllegalArgumentException("There are only $numOfBoards levels, can't return level number $levelIndex")
        }

        if (levelIndex >= loadedLevels.size) {
            var i = loadedLevels.size - 1
            var error = false
            while (!error && i < levelIndex) {
                val nextLine = readLine()
                if (nextLine != null) {
                    loadedLevels.add(nextLine)
                    i++
                } else {
                    error = true
                }
            }
        }

        return loadedLevels[levelIndex]
    }

    open fun close() {
        scanner?.close()
        inputStream?.close()
    }

    private fun readLine(): String? =
        try {
            scanner?.nextLine()
        } catch (e: NoSuchElementException) {
            null
        }
}