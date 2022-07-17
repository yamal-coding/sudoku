package com.yamal.sudoku.game.domain

import com.yamal.sudoku.model.SudokuCellValue

sealed class Movement {
    abstract val row: Int
    abstract val column: Int

    abstract fun isTheSameAs(otherMovement: Movement): Boolean
}

data class SetValueMovement(
    override val row: Int,
    override val column: Int,
    val previousCellState: PreviousCellState,
    val newValue: SudokuCellValue,
) : Movement() {
    override fun isTheSameAs(otherMovement: Movement): Boolean =
        otherMovement is SetValueMovement
            && row == otherMovement.row && column == otherMovement.column
            && newValue == otherMovement.newValue
}

sealed class PreviousCellState {
    data class SingleValue(
        val value: SudokuCellValue
    ) : PreviousCellState()
    data class Possibilities(
        val possibilities: Set<SudokuCellValue>
    ) : PreviousCellState()
}

data class SetPossibilitiesMovement(
    override val row: Int,
    override val column: Int,
    val previousCellValue: SudokuCellValue?,
    val newPossibilities: Set<SudokuCellValue>,
    val shouldAddPossibilities: Boolean,
) : Movement() {
    override fun isTheSameAs(otherMovement: Movement): Boolean =
        otherMovement is SetPossibilitiesMovement
            && row == otherMovement.row && column == otherMovement.column
            && newPossibilities == otherMovement.newPossibilities
            && shouldAddPossibilities == otherMovement.shouldAddPossibilities
}
