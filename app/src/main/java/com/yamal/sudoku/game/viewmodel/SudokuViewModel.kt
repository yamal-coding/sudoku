package com.yamal.sudoku.game.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yamal.sudoku.game.status.domain.AddOrRemovePossibilityToSelectedCell
import com.yamal.sudoku.game.status.domain.ClearBoard
import com.yamal.sudoku.game.status.domain.GetCurrentGameState
import com.yamal.sudoku.game.status.domain.LoadNewBoard
import com.yamal.sudoku.game.status.domain.LoadSavedBoard
import com.yamal.sudoku.game.status.domain.SelectCell
import com.yamal.sudoku.game.status.domain.SudokuState
import com.yamal.sudoku.game.status.domain.Undo
import com.yamal.sudoku.game.status.domain.UpdateSelectedCell
import com.yamal.sudoku.model.Difficulty
import com.yamal.sudoku.model.SudokuCellValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SudokuViewModel @Inject constructor(
    getCurrentGameState: GetCurrentGameState,
    private val loadSavedBoard: LoadSavedBoard,
    private val loadNewBoard: LoadNewBoard,
    private val undo: Undo,
    private val clearBoard: ClearBoard,
    private val selectCell: SelectCell,
    private val updateSelectedCell: UpdateSelectedCell,
    private val addOrRemovePossibilityToSelectedCell: AddOrRemovePossibilityToSelectedCell,
) : ViewModel() {

    val state: Flow<SudokuViewState> = getCurrentGameState().map {
        when (it) {
            is SudokuState.Idle -> SudokuViewState.Idle
            is SudokuState.Loading -> SudokuViewState.Loading
            is SudokuState.NewBoardNotFound -> SudokuViewState.NewBoardNotFound
            is SudokuState.SavedGameNotFound -> SudokuViewState.SavedGameNotFound
            is SudokuState.UpdatedBoard -> SudokuViewState.UpdatedBoard(
                board = it.board,
                selectedRow = it.selectedRow,
                selectedColumn = it.selectedColumn,
                canUndo = it.canUndo,
                gameHasFinished = it.gameHasFinished,
            )
        }
    }

    private val _shouldShowClearBoardConfirmationDialog = MutableStateFlow(false)
    val shouldShowClearBoardConfirmationDialog: Flow<Boolean> =
        _shouldShowClearBoardConfirmationDialog

    private val _isPossibilitiesModeEnabled = MutableStateFlow(false)
    val isPossibilitiesModeEnabled: Flow<Boolean> = _isPossibilitiesModeEnabled

    private val _shouldShowNewGameButtons = MutableStateFlow(false)
    val shouldShowNewGameButtons: Flow<Boolean> = _shouldShowNewGameButtons

    fun initNewGame(difficulty: Difficulty) {
        viewModelScope.launch {
            loadNewBoard(difficulty)
        }
    }

    fun initExistingGame() {
        viewModelScope.launch {
            loadSavedBoard()
        }
    }

    fun onCellSelected(row: Int, column: Int) {
        selectCell(selectedRow = row, selectedColumn = column)
    }

    fun setCellValue(value: SudokuCellValue) {
        if (_isPossibilitiesModeEnabled.value) {
            addOrRemovePossibilityToSelectedCell(value)
        } else {
            updateSelectedCell(value)
        }
    }

    fun onUndo() {
        undo()
    }

    fun showClearBoardConfirmationDialog() {
        _shouldShowClearBoardConfirmationDialog.value = true
    }

    fun hideClearBoardConfirmationDialog() {
        _shouldShowClearBoardConfirmationDialog.value = false
    }

    fun onClear() {
        hideClearBoardConfirmationDialog()
        clearBoard()
    }

    fun onEnablePossibilitiesMode() {
        _isPossibilitiesModeEnabled.value = true
    }

    fun onDisablePossibilitiesMode() {
        _isPossibilitiesModeEnabled.value = false
    }

    fun onPLayAgain() {
        _shouldShowNewGameButtons.value = true
    }
}
