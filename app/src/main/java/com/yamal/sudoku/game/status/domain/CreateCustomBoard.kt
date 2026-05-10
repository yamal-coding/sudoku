package com.yamal.sudoku.game.status.domain

import com.yamal.sudoku.game.domain.Board
import com.yamal.sudoku.game.navigation.GameNavigationParams
import com.yamal.sudoku.game.status.data.GameStatusRepository
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.model.SudokuCell
import com.yamal.sudoku.model.SudokuCellValue
import java.util.UUID
import javax.inject.Inject

open class CreateCustomBoard @Inject constructor(
    private val gameStatusRepository: GameStatusRepository,
) {
    open suspend operator fun invoke(cells: List<SudokuCell>): GameNavigationParams {
        val gameId = UUID.randomUUID().toString()
        val fixedCells = cells.map { cell ->
            cell.copy(isFixed = cell.value != SudokuCellValue.EMPTY)
        }.toMutableList()
        val board = Board(cells = fixedCells, difficulty = Difficulty.CUSTOM)

        gameStatusRepository.setGameId(gameId)
        gameStatusRepository.saveBoard(board)
        gameStatusRepository.saveTimeCounter(0L)

        return GameNavigationParams(gameId = gameId, difficulty = Difficulty.CUSTOM)
    }
}
