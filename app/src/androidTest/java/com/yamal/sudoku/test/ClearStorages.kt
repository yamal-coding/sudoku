package com.yamal.sudoku.test

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ClearStorages @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    operator fun invoke() {
       val sharedPreferences = context.getSharedPreferences("sudoku_board_storage", Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .clear()
            .commit()
    }
}