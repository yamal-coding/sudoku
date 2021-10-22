package com.yamal.sudoku.game.status.data.storage.model

// TODO add serialized names

data class SudokuCellDO(
    val value: Int,
    val isFixed: Boolean
)

data class BoardDO(
    val cells: List<List<SudokuCellDO>>,
    val difficulty: String?
)

object DifficultyDO {
    const val EASY = "easy"
    const val MEDIUM = "medium"
    const val HARD = "hard"
}