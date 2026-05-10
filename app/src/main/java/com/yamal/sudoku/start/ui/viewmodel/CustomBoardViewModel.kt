package com.yamal.sudoku.start.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yamal.sudoku.game.domain.Board
import com.yamal.sudoku.game.domain.BOARD_SIDE
import com.yamal.sudoku.game.navigation.GameNavigationParams
import com.yamal.sudoku.game.status.data.GameStatusRepository
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.model.SudokuCell
import com.yamal.sudoku.model.SudokuCellValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class CustomBoardState(
    val cells: List<SudokuCell>,
    val selectedRow: Int?,
    val selectedColumn: Int?,
    val showStartGameDialog: Boolean,
)

@HiltViewModel
class CustomBoardViewModel @Inject constructor(
    private val gameStatusRepository: GameStatusRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(
        CustomBoardState(
            cells = List(BOARD_SIDE * BOARD_SIDE) { SudokuCell(SudokuCellValue.EMPTY, isFixed = false) },
            selectedRow = null,
            selectedColumn = null,
            showStartGameDialog = false,
        )
    )
    val state: StateFlow<CustomBoardState> = _state

    fun onCellSelected(row: Int, column: Int) {
        _state.value = _state.value.copy(selectedRow = row, selectedColumn = column)
    }

    fun onNumberSelected(value: SudokuCellValue) {
        val row = _state.value.selectedRow ?: return
        val column = _state.value.selectedColumn ?: return

        val cells = _state.value.cells.toMutableList()
        val index = row * BOARD_SIDE + column
        cells[index] = SudokuCell(value = value, isFixed = false)
        _state.value = _state.value.copy(cells = cells)
    }

    fun onRequestStartGame() {
        _state.value = _state.value.copy(showStartGameDialog = true)
    }

    fun onDismissStartGameDialog() {
        _state.value = _state.value.copy(showStartGameDialog = false)
    }

    fun saveAndStartGame(onReady: (GameNavigationParams) -> Unit) {
        val gameId = UUID.randomUUID().toString()
        val fixedCells = _state.value.cells.map { cell ->
            cell.copy(isFixed = cell.value != SudokuCellValue.EMPTY)
        }.toMutableList()
        val board = Board(cells = fixedCells, difficulty = Difficulty.CUSTOM)

        viewModelScope.launch {
            gameStatusRepository.setGameId(gameId)
            gameStatusRepository.saveBoard(board)
            onReady(GameNavigationParams(gameId = gameId, difficulty = Difficulty.CUSTOM))
        }
    }
}
