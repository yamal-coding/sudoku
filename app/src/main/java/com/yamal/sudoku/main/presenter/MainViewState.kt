package com.yamal.sudoku.main.presenter

sealed class MainViewState {
    object LoadingGame : MainViewState()
    object SavedGame : MainViewState()
    object NotSavedGame : MainViewState()
}
