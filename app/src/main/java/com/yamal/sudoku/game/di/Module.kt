package com.yamal.sudoku.game.di

import android.content.Context
import com.yamal.sudoku.game.domain.DoNotShowSetUpNewGameHintAgain
import com.yamal.sudoku.game.domain.GetSavedBoard
import com.yamal.sudoku.game.domain.RemoveSavedBoard
import com.yamal.sudoku.game.domain.SaveBoard
import com.yamal.sudoku.game.domain.ShouldShowSetUpNewGameHint
import com.yamal.sudoku.game.presenter.SudokuPresenter
import com.yamal.sudoku.game.view.FeedbackFactory
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val gameScreenModule = module {
    factory { GetSavedBoard(repository = get()) }

    factory { SaveBoard(repository = get()) }

    factory { RemoveSavedBoard(repository = get()) }

    factory { (context: Context) -> FeedbackFactory(context) }

    factory { ShouldShowSetUpNewGameHint(repository = get()) }

    factory { DoNotShowSetUpNewGameHintAgain(repository = get()) }

    factory { (context: Context) ->
        SudokuPresenter(
            getSavedBoard = get(),
            saveBoard = get(),
            removeSavedBoard = get(),
            feedbackFactory = get { parametersOf(context) },
            shouldShowSetUpNewGameHint = get(),
            doNotShowSetUpNewGameHintAgain = get(),
            dispatchers = get()
        )
    }
}
