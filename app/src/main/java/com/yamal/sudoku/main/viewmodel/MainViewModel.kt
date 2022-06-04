package com.yamal.sudoku.main.viewmodel

import com.yamal.sudoku.commons.thread.CoroutineDispatcherProvider
import com.yamal.sudoku.main.Navigator
import com.yamal.sudoku.main.domain.HasSavedBoard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val hasSavedBoard: HasSavedBoard,
    private val navigator: Navigator,
    dispatchers: CoroutineDispatcherProvider
) {

    private val _state = MutableStateFlow<MainViewState>(MainViewState.LoadingGame)
    val state: StateFlow<MainViewState> = _state

    private val job = Job()
    private val scope = CoroutineScope(job + dispatchers.mainDispatcher)

    fun onResume() {
        scope.launch {
            if (hasSavedBoard()) {
                _state.value = MainViewState.SavedGame
            } else {
                _state.value = MainViewState.NotSavedGame
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