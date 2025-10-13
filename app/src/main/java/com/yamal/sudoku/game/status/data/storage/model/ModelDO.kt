package com.yamal.sudoku.game.status.data.storage.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SudokuCellDO(
    @param:Json(name = "value") val value: Int,
    @param:Json(name = "isFixed") val isFixed: Boolean,
    @param:Json(name = "possibilities") val possibilities: List<Int>? = null,
)

@JsonClass(generateAdapter = true)
data class BoardDO(
    @param:Json(name = "cells") val cells: List<SudokuCellDO>,
    @param:Json(name = "difficulty") val difficulty: String,
)

object DifficultyDO {
    const val EASY = "easy"
    const val MEDIUM = "medium"
    const val HARD = "hard"
}

@JsonClass(generateAdapter = true)
data class LastFinishedGameSummaryDO(
    @param:Json(name = "gameId") val gameId: String,
    @param:Json(name = "gameTimeInSeconds") val gameTimeInSeconds: Long?,
    @param:Json(name = "isNewBestTime") val isNewBestTime: Boolean,
)
