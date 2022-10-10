package com.yamal.sudoku.game.status.domain

import javax.inject.Inject

open class SelectCell @Inject constructor(
    private val currentGame: CurrentGame,
) {
    open operator fun invoke(selectedRow: Int, selectedColumn: Int) {
        if (!currentGame.hasFinished()) {
            currentGame.onSelectCell(selectedRow = selectedRow, selectedColumn = selectedColumn)
        }
    }
}
