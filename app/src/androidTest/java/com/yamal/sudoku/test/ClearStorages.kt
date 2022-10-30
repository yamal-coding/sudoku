package com.yamal.sudoku.test

import androidx.datastore.preferences.core.edit
import com.yamal.sudoku.commons.storage.GlobalDataStorage
import com.yamal.sudoku.game.level.data.datasource.db.LevelsDatabase
import javax.inject.Inject
import kotlinx.coroutines.runBlocking

class ClearStorages @Inject constructor(
    private val globalDataStorage: GlobalDataStorage,
    private val levelsDatabase: LevelsDatabase,
) {
    operator fun invoke() {
        runBlocking {
            globalDataStorage.edit {
                it.clear()
            }
        }
        levelsDatabase.clearAllTables()
    }
}
