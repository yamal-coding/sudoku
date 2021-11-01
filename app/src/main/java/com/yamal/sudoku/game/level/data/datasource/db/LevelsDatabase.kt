package com.yamal.sudoku.game.level.data.datasource.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Level::class], version = 1)
abstract class LevelsDatabase : RoomDatabase() {
    abstract fun levelsDao(): LevelsDao
}