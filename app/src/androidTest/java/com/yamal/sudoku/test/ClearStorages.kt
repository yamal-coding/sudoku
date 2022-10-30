package com.yamal.sudoku.test

import androidx.datastore.preferences.core.edit
import com.yamal.sudoku.commons.storage.GlobalDataStorage
import javax.inject.Inject
import kotlinx.coroutines.runBlocking

class ClearStorages @Inject constructor(
    private val globalDataStorage: GlobalDataStorage,
) {
    operator fun invoke() {
        runBlocking {
            globalDataStorage.edit {
                it.clear()
            }
        }
    }
}
