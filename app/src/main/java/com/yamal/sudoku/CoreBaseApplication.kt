package com.yamal.sudoku

import com.yamal.sudoku.darkmode.InitDarkMode
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CoreBaseApplication : SudokuBaseApplication() {

    @Inject
    lateinit var injectedInitDarkMode: InitDarkMode
    override val initDarkMode: InitDarkMode
        get() = injectedInitDarkMode
}