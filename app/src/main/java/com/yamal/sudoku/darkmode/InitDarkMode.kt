package com.yamal.sudoku.darkmode

import androidx.appcompat.app.AppCompatDelegate
import com.yamal.sudoku.BuildConfig
import javax.inject.Inject

class InitDarkMode @Inject constructor() {
    operator fun invoke() {
        val nightMode = if (BuildConfig.IS_DARK_MODE_ALLOWEDD) {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }

        AppCompatDelegate.setDefaultNightMode(nightMode)
    }
}
