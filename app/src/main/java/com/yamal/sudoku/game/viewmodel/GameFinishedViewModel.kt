package com.yamal.sudoku.game.viewmodel

import androidx.lifecycle.ViewModel
import com.yamal.sudoku.game.status.domain.GetFinishedGameSummary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class GameFinishedViewModel @Inject constructor(
    private val getFinishedGameSummary: GetFinishedGameSummary,
) : ViewModel() {
    private val _shouldShowNewGameButtons = MutableStateFlow(false)
    val shouldShowNewGameButtons: Flow<Boolean> = _shouldShowNewGameButtons

    fun onPLayAgain() {
        _shouldShowNewGameButtons.value = true
    }
}
