package com.yamal.sudoku

import android.app.Application
import com.yamal.sudoku.commons.thread.di.threadingModule
import com.yamal.sudoku.game.di.gameScreenModule
import com.yamal.sudoku.main.di.mainScreenModule
import com.yamal.sudoku.repository.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SudokuApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SudokuApplication)
            modules(
                repositoryModule,
                threadingModule,
                mainScreenModule,
                gameScreenModule
            )
        }
    }
}
