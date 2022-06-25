package com.yamal.sudoku.game.status.data.storage.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SudokuCellDO(
    @Json(name = "value") val value: Int,
    @Json(name = "isFixed") val isFixed: Boolean
)

@JsonClass(generateAdapter = true)
data class BoardDO(
    @Json(name = "cells") val cells: List<SudokuCellDO>,
)
