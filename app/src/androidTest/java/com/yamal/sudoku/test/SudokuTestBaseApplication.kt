package com.yamal.sudoku.test

import com.yamal.sudoku.SudokuBaseApplication
import com.yamal.sudoku.darkmode.InitDarkMode
import dagger.hilt.InstallIn
import dagger.hilt.android.EarlyEntryPoint
import dagger.hilt.android.EarlyEntryPoints
import dagger.hilt.components.SingletonComponent

open class SudokuTestBaseApplication : SudokuBaseApplication() {
    override val initDarkMode: InitDarkMode
        get() = EarlyEntryPoints.get(this, TestEntryPoint::class.java).initDarkMode()

    @EarlyEntryPoint
    @InstallIn(SingletonComponent::class)
    internal interface TestEntryPoint {
        fun initDarkMode(): InitDarkMode
    }
}