package com.yamal.sudoku.main.viewmodel

sealed class MainViewState {
    object LoadingGame : MainViewState()
    object SavedGame : MainViewState()
    object NotSavedGame : MainViewState()
}
