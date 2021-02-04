package com.yamal.sudoku.main.presenter

import com.yamal.sudoku.commons.thread.CoroutineDispatcherProvider
import com.yamal.sudoku.main.Navigator
import com.yamal.sudoku.main.domain.HasSavedBoard
import com.yamal.sudoku.main.view.MainView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainPresenter(
    private val hasSavedBoard: HasSavedBoard,
    private val navigator: Navigator,
    dispatchers: CoroutineDispatcherProvider
) {

    private val job = Job()
    private val scope = CoroutineScope(job + dispatchers.mainDispatcher)

    private lateinit var view: MainView

    fun onCreate(view: MainView) {
        this.view = view

        scope.launch {
            if (hasSavedBoard()) {
                view.onSavedGame()
            } else {
                view.onNotSavedGame()
            }
        }
    }

    fun openNewGame() {
        navigator.openNewGame()
    }

    fun openSavedGame() {
        navigator.openSavedGame()
    }

    fun onDestroy() {
        job.cancel()
    }
}