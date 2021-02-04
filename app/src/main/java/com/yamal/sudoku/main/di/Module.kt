package com.yamal.sudoku.main.di

import android.content.Context
import com.yamal.sudoku.main.Navigator
import com.yamal.sudoku.main.domain.HasSavedBoard
import com.yamal.sudoku.main.presenter.MainPresenter
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val mainScreenModule = module {
    factory { (context: Context) -> Navigator(context) }

    factory { HasSavedBoard(repository = get()) }

    factory { (context: Context) ->
        MainPresenter(
            hasSavedBoard = get(),
            navigator = get { parametersOf(context) },
            dispatchers = get()
        )
    }
}