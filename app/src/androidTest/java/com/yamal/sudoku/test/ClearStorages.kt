package com.yamal.sudoku.test

import android.content.SharedPreferences
import javax.inject.Inject

class ClearStorages @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke() {
        sharedPreferences.edit()
            .clear()
            .commit()
    }
}
