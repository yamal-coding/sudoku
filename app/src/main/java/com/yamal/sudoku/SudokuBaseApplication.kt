package com.yamal.sudoku

import android.app.Application
import com.yamal.sudoku.darkmode.InitDarkMode

abstract class SudokuBaseApplication : Application() {

    abstract val initDarkMode: InitDarkMode

    override fun onCreate() {
        super.onCreate()

        initDarkMode()
    }
}
