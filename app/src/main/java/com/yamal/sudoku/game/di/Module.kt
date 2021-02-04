package com.yamal.sudoku.game.di

import com.yamal.sudoku.game.domain.GetSavedBoard
import com.yamal.sudoku.game.domain.RemoveSavedBoard
import com.yamal.sudoku.game.domain.SaveBoard
import com.yamal.sudoku.game.presenter.SudokuPresenter
import org.koin.dsl.module

val gameScreenModule = module {
    factory { GetSavedBoard(repository = get()) }

    factory { SaveBoard(repository = get()) }

    factory { RemoveSavedBoard(repository = get()) }

    factory {
        SudokuPresenter(
            getSavedBoard = get(),
            saveBoard = get(),
            removeSavedBoard = get(),
            dispatchers = get()
        )
    }
}