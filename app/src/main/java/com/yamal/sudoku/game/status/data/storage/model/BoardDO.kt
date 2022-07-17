package com.yamal.sudoku.game.status.data.storage.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SudokuCellDO(
    @Json(name = "value") val value: Int,
    @Json(name = "isFixed") val isFixed: Boolean,
    @Json(name = "possibilities") val possibilities: List<Int>? = null,
)

@JsonClass(generateAdapter = true)
data class BoardDO(
    @Json(name = "cells") val cells: List<SudokuCellDO>,
    @Json(name = "difficulty") val difficulty: String,
)

object DifficultyDO {
    const val EASY = "easy"
    const val MEDIUM = "medium"
    const val HARD = "hard"
}
