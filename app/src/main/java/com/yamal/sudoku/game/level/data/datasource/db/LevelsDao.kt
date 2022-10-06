package com.yamal.sudoku.game.level.data.datasource.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LevelsDao {
    @Query("SELECT `index` FROM Level WHERE levels_file == :fileName")
    fun getAlreadyReturnedLevelsIndexesForGivenFile(fileName: String): List<Int>

    @Insert
    fun markLevelAsAlreadyReturned(level: Level)

    @Query("DELETE FROM Level WHERE levels_file LIKE :fileNamePattern")
    fun deleteAlreadyReturnedLevelsForGivenFileNamePattern(fileNamePattern: String)
}
