package com.yamal.sudoku.start.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yamal.sudoku.game.domain.BOARD_SIDE
import com.yamal.sudoku.game.navigation.GameNavigationParams
import com.yamal.sudoku.game.status.domain.CreateCustomBoard
import com.yamal.sudoku.model.SudokuCell
import com.yamal.sudoku.model.SudokuCellValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CustomBoardState(
    val cells: List<SudokuCell>,
    val selectedRow: Int?,
    val selectedColumn: Int?,
    val showStartGameDialog: Boolean,
)

@HiltViewModel
class CustomBoardViewModel @Inject constructor(
    private val createCustomBoard: CreateCustomBoard,
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
        viewModelScope.launch {
            val params = createCustomBoard(_state.value.cells)
            onReady(params)
        }
    }
}
