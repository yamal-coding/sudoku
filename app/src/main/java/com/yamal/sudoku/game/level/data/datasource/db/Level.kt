package com.yamal.sudoku.game.level.data.datasource.db

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["levels_file, index"])
data class Level(
    @ColumnInfo(name = "levels_file") val levelsFile: String,
    @ColumnInfo(name = "index") val index: Int
)
