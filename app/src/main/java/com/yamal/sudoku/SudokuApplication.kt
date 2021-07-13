package com.yamal.sudoku

import android.app.Application
import com.yamal.sudoku.darkmode.InitDarkMode
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class SudokuApplication : Application() {

    @Inject
    lateinit var initDarkMode: InitDarkMode

    override fun onCreate() {
        super.onCreate()

        initDarkMode()
    }
}
