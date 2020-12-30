package com.yamal.sudoku.presenter

import com.yamal.sudoku.commons.JobDispatcher
import com.yamal.sudoku.navigation.Navigator
import com.yamal.sudoku.usecase.HasSavedBoard
import com.yamal.sudoku.view.MainView

class MainPresenter(
    private val hasSavedBoard: HasSavedBoard,
    private val navigator: Navigator,
    private val jobDispatcher: JobDispatcher
) {
    private lateinit var view: MainView

    fun onCreate(view: MainView) {
        this.view = view
        hasSavedBoard(::onSavedBoard, ::onNotSavedBoard)
    }

    private fun onSavedBoard() {
        jobDispatcher.runOnUIThread(view::onSavedGame)
    }

    private fun onNotSavedBoard() {
        jobDispatcher.runOnUIThread(view::onNotSavedGame)
    }

    fun openNewGame() {
        navigator.openNewGame()
    }

    fun openSavedGame() {
        navigator.openSavedGame()
    }
}