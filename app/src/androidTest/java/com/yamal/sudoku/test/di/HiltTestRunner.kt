package com.yamal.sudoku.test.di

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.yamal.sudoku.test.SudokuTestBaseApplication
import dagger.hilt.android.testing.CustomTestApplication

class HiltTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application =
        super.newApplication(cl, CustomHiltTestApplication_Application::class.java.name, context)
}

@CustomTestApplication(SudokuTestBaseApplication::class)
interface CustomHiltTestApplication