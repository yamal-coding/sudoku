package com.yamal.sudoku.start.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.yamal.sudoku.start.domain.HasSavedBoard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val hasSavedBoard: HasSavedBoard,
) : ViewModel() {

    val startScreenState: Flow<StartScreenState>
        get() = hasSavedBoard().map { hasSavedBoard ->
            StartScreenState.Ready(shouldShowContinueButton = hasSavedBoard)
        }
}

sealed class StartScreenState {
    object Idle : StartScreenState()
    data class Ready(
        val shouldShowContinueButton: Boolean
    ): StartScreenState()
}